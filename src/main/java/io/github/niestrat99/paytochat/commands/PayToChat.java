package io.github.niestrat99.paytochat.commands;

import io.github.niestrat99.paytochat.Main;
import io.github.niestrat99.paytochat.commands.subcommands.ChatPrice;
import io.github.niestrat99.paytochat.commands.subcommands.Help;
import io.github.niestrat99.paytochat.commands.subcommands.WhitelistWorld;
import io.github.niestrat99.paytochat.config.Config;
import io.github.niestrat99.paytochat.utils.MessageHandler;
import io.github.niestrat99.paytochat.utils.VaultHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PayToChat implements TabExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                switch (args[0]) {
                    case "reload" -> {
                        if (!Main.hasPerms(player, "ptc.reload")) {
                            return false;
                        }
                        MessageHandler.info(player, "Reloading configurations...");
                        Config.reloadConfig();
                        MessageHandler.success(player, "Reload complete!");
                    }

                    case "chatprice" -> {
                        Main.debug("Check permission for " + player.getName());
                        if (!Main.hasPerms(player, "ptc.chatprice")) {
                            return false;
                        }
                        if (args.length > 1) {
                            Main.debug("Validating if entered argument is a double value.");
                            if (MessageHandler.validateDouble(args[1])) {
                                double newPrice = Double.parseDouble(args[1]);
                                Main.debug("Price was identified and is " + newPrice);
                                ChatPrice.setChatPrice(player, newPrice);
                            } else {
                                MessageHandler.error(player, "Argument must be a double value like '1.0'!");
                                return false;
                            }
                        } else {
                            MessageHandler.error(player, "Too few arguments! /ptc chatprice <newPrice>");
                        }
                    }

                    case "command" -> {
                        if (!Main.hasPerms(player, "ptc.command")) {
                            return false;
                        }
                        if (args.length > 2) {
                            String commandName = args[1];
                            if (MessageHandler.validateDouble(args[2])) {
                                double price = Double.parseDouble(args[2]);
                                Config.setCommandPrice(commandName, price);
                                MessageHandler.success(player, "Successfully set price for command &6" + commandName + " &ato &6" + VaultHandler.economy.format(price) + "&a!");
                            } else {
                                MessageHandler.error(player, "Value must be a number! (e.g. 1 or 1.5)");
                                return false;
                            }
                        } else {
                            if (!args[1].isEmpty() && Config.getCommands().contains(args[1])) {
                                MessageHandler.info(player, "Price of command &6/" + args[1] + "&7: &6" + VaultHandler.economy.format(Config.getCommandPrice(args[1])));
                                return false;
                            }
                            MessageHandler.error(player, "Not enough arguments!");
                            return false;
                        }
                    }

                    case "wlworld" -> {
                        if (!Main.hasPerms(player, "ptc.whitelistworld")) {return false;}
                        if (args.length > 2) {
                            String worldName = args[2];
                            if (args[1].equals("add")) {
                                if (WhitelistWorld.addWorld(player, worldName)) {
                                    MessageHandler.success(player, "Added &6" + worldName + " &ato the whitelist!");
                                }

                            } else if (args[1].equals("remove")) {
                                if (WhitelistWorld.removeWorld(player, worldName)) {
                                    MessageHandler.success(player, "Removed &6" + worldName + " &afrom the whitelist!");
                                }
                            } else {
                                MessageHandler.error(player, "Argument must be either 'add' or 'remove'!");
                                return false;
                            }
                        }
                    }
                    default -> MessageHandler.error(player, "Too few arguments!");
                }
            } else {
                if (!Main.hasPerms(player, "ptc.help")) {
                    return false;
                }
                Help.sendCommands(player);
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return commandSuggestions;
        }
        if (args.length == 2) {
            if (args[0].equals("command")) {
                for (String cmd : Config.getCommands()) {
                    if (!commands.contains(cmd)) {
                        commands.add(cmd);
                    }
                }
                return commands;
            }
            if (args[0].equals("wlworld")) {
                return addRemoveSuggestion;
            }
        }
        if (args.length == 3) {
            if (args[0].equals("wlworld")) {
                for (World world : Bukkit.getWorlds()) {
                    if (!worlds.contains(world.getName())) {
                        worlds.add(world.getName());
                    }
                }

            }
            return worlds;
        }
        return null;
    }

    private static final List<String> commandSuggestions = new ArrayList<>(Arrays.asList(
            "reload",
            "chatprice",
            "command",
            "wlworld"
    ));

    private static final List<String> addRemoveSuggestion = new ArrayList<>(Arrays.asList(
            "add",
            "remove"
    ));

    private static final List<String> worlds = new ArrayList<>();
    private static final List<String> commands = new ArrayList<>();
}
