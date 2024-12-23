package io.github.niestrat99.paytochat.commands.subcommands;

import io.github.niestrat99.paytochat.config.Config;
import io.github.niestrat99.paytochat.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WhitelistWorld {
    public static boolean worldExists(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return true;
        }
        return false;
    }

    public static boolean addWorld(Player player, String worldName) {
        if (!worldExists(worldName)) {
            MessageHandler.error(player, "World does not exist!");
            return false;
        }
        if (Config.getWhitelist().contains(worldName)) {
            MessageHandler.error(player, "World is already whitelisted!");
            return false;
        }
        Config.addWorld(worldName);
        return true;
    }

    public static boolean removeWorld(Player player, String worldName) {
        if (!worldExists(worldName)) {
            MessageHandler.error(player, "World does not exist!");
            return false;
        }
        if (!Config.getWhitelist().contains(worldName)) {
            MessageHandler.error(player, "World is not whitelisted!");
            return false;
        }
        Config.removeWorld(worldName);
        return true;
    }
}
