package io.github.niestrat99.paytochat.commands.subcommands;

import io.github.niestrat99.paytochat.utils.MessageHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help {
    public static void sendCommands(Player player) {
        MessageHandler.info(player, "&6--{&7Commands&6}--");
        cmd(player, "/ptc reload", "Reloads the config file.");
        cmd(player, "/ptc chatprice <price>", "Changes the chat price.");
        cmd(player, "/ptc command <commandName> <price>", "Changes the price for a command.");
        cmd(player, "/ptc wlworld <add/remove> <worldName>", "Adds the specified world to the world whitelist.");
    }
    private static void cmd(Player player, String command, String description) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', command + " &6>> &7" + description));
    }
}
