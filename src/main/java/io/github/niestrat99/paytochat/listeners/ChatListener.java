package io.github.niestrat99.paytochat.listeners;

import io.github.niestrat99.paytochat.config.Config;
import io.github.niestrat99.paytochat.utils.VaultHandler;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("ptc.bypass") || !isInWhitelistedWorld(player)) {return;}

        double price = Config.getChatPrice();

        if (price > 0 && !VaultHandler.processPayment(player, price)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommandInput(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("ptc.bypass") || !isInWhitelistedWorld(player)) {return;}

        String message = e.getMessage().toLowerCase();
        String command = message.split(" ")[0].substring(1);

        double price = Config.getCommandPrice(command);

        if (price > 0 && !VaultHandler.processPayment(player, price)) {
            e.setCancelled(true);
        }
    }

    private static boolean isInWhitelistedWorld(Player player) {
        if (Config.getWhitelist().contains(player.getWorld().getName())) {
            return true;
        }
        return false;
    }
}
