package io.github.niestrat99.paytochat.commands.subcommands;

import io.github.niestrat99.paytochat.Main;
import io.github.niestrat99.paytochat.config.Config;
import io.github.niestrat99.paytochat.utils.MessageHandler;
import io.github.niestrat99.paytochat.utils.VaultHandler;
import org.bukkit.entity.Player;

public class ChatPrice {

    public static void setChatPrice(Player player, Double newPrice) {
        double currentPrice = Config.getChatPrice();
        Main.debug("Current chat price is " + currentPrice);
        Main.debug("Attempting to set chat price to " + newPrice);

        if (newPrice == currentPrice) {
            Main.debug("New price is identical, aborting action.");
            MessageHandler.error(player, "The new price is identical with the current chat price!");
            return;
        }
        if (newPrice < 0) {
            Main.debug("New price is somehow a negative number. How!? Aborting action.");
            MessageHandler.error(player, "Chat price cannot be below 0!");
            return;
        }

        Config.setChatPrice(newPrice);
        Main.debug("Setting new chat price was successful.");
        MessageHandler.success(player, "Successfully set new chat price to &6" + VaultHandler.economy.format(newPrice) + "&a!");
    }
}
