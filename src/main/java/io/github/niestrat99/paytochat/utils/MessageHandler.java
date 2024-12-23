package io.github.niestrat99.paytochat.utils;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.block.Vault;
import org.bukkit.entity.Player;

public class MessageHandler {
    private static final String title = "&6Pay&72&6Chat &8>> ";

    public static void info(Player player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', title + "&7" + msg));
    }

    public static void error(Player player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', title + "&c" + msg));
    }

    public static void success(Player player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', title + "&a" + msg));
    }

    public static boolean validateDouble(String argumentSection) {
        if (argumentSection.matches("^(\\d+.\\d+)$")) {
            return true;
        }
        return false;
    }

    public static void sendBalanceUpdate(Player player, Double amountPaid) {
        String balance = VaultHandler.economy.format(VaultHandler.economy.getBalance(player));
        String formatPaid = VaultHandler.economy.format(amountPaid);

        player.sendActionBar(ChatColor.translateAlternateColorCodes('&', "&7Paid &6" + formatPaid + "&7. New balance: &6" + balance + "&7."));
    }
}
