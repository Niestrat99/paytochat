package io.github.niestrat99.paytochat;

import io.github.niestrat99.paytochat.commands.PayToChat;
import io.github.niestrat99.paytochat.config.Config;
import io.github.niestrat99.paytochat.listeners.ChatListener;
import io.github.niestrat99.paytochat.utils.MessageHandler;
import io.github.niestrat99.paytochat.utils.VaultHandler;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        log(Level.INFO, "Setting up plugin Pay2Chat.");
        log(Level.INFO, "Checking for Vault and a valid economy plugin...");
        if (!VaultHandler.vaultAndEconomyInstalled()) {
            instance.setEnabled(false);
        }

        log(Level.INFO, "Register events...");
        regEvent(new ChatListener());

        log(Level.INFO, "Register commands...");
        regCmd("ptc", new PayToChat());

        log(Level.INFO, "Setting up configurations..");
        Config.loadConfig();
    }

    public static Main get() {
        return instance;
    }

    public static void debug(String message) {
        if (Config.debugEnabled()) {
            log(Level.INFO, "[DEBUG] " + message);
        }
    }

    public static void log(Level level, String message) {
        instance.getLogger().log(level, message);
    }

    public static void log(Level level, String message, Class<?> classFile, Exception e) {
        String output = message + "\nFrom class: " + classFile.getName();
        if (e != null) {
            output = output.concat("\nStacktrace:\n" + e);
        }
        instance.getLogger().log(level, output);
    }

    private static void regCmd(String commandName, CommandExecutor commandClass) {
        Objects.requireNonNull(instance.getCommand(commandName)).setExecutor(commandClass);
    }

    private static void regEvent(Listener eventClass) {
        instance.getServer().getPluginManager().registerEvents(eventClass, instance);
    }

    public static boolean hasPerms(Player player, String permission) {
        debug("Permission node: " + permission);
        if (player.hasPermission(permission)) {
            debug("Player has permission!");
            return true;
        }
        debug("Player has no permission!");
        MessageHandler.error(player, "You do not have permission to use this command!");
        return false;
    }
}
