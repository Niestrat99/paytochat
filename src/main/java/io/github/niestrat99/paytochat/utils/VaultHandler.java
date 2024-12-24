package io.github.niestrat99.paytochat.utils;

import io.github.niestrat99.paytochat.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Level;

public class VaultHandler {
    public static Economy economy;
    private static final Server server = Main.get().getServer();

    public static boolean vaultAndEconomyInstalled() {
        if (server.getPluginManager().getPlugin("Vault") == null) {
            Main.log(Level.SEVERE, "Vault plugin not detected! Plugin will be disabled.");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Main.log(Level.SEVERE, "Economy plugin not found! Plugin will be disabled.");
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public static boolean processPayment(Player player, Double amount) {
        double bal = economy.getBalance(player);

        if (bal >= amount) {
            if (bal < 2 * amount) {
                Main.debug(player.getName() + " no longer has enough funds!");
                MessageHandler.info(player, "You no longer have enough funds for this action!");
            }
            Main.debug(player.getName() + " has enough funds!");
            economy.withdrawPlayer(player, amount);
            Main.debug("Withdrawn " + economy.format(amount) + " from " + player.getName());
            MessageHandler.sendBalanceUpdate(player, amount);
            return true;
        } else {
            Main.debug("Player is lacking funds for this action.");
            Main.debug("Player's balance: " + bal + " | Price of action: " + amount);
            double missing = Math.abs(bal - amount);
            MessageHandler.error(player, "You need &6" + economy.format(missing) + " &cmore to do this action!");
            return false;
        }
    }
}
