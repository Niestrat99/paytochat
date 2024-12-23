package io.github.niestrat99.paytochat.config;

import io.github.niestrat99.paytochat.Main;
import io.github.thatsmusic99.configurationmaster.api.ConfigFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class Config extends ConfigFile {
    public static Config configFile;
    private static final String separator = "[]>-----------------------------------------------------------------------------<[]";

    public Config(@NotNull File file) throws IOException, IllegalAccessException {
        super(file);
    }

    public static void validateFile() {
        if (!Main.get().getDataFolder().exists()) {
            boolean createFolderSuccess = Main.get().getDataFolder().mkdir();
            if (!createFolderSuccess) {
                Main.log(Level.SEVERE, "Failed to create DataFolder for Pay2Chat. Please contact the developer about this.");
            }
        }
    }

    public static void loadConfig() {
        try {
            validateFile();
            Config config = new Config(new File(Main.get().getDataFolder(), "config.yml"));
            configFile = config;
            config.load();
        } catch (Exception e) {
            Main.log(Level.SEVERE, "Failed to load configurations.", Config.class, e);
        }
    }

    public static void reloadConfig() {
        try {
            configFile.reload();
        } catch (Exception e) {
            Main.log(Level.SEVERE, "Failed to reload configurations.", Config.class, e);
        }
    }

    @Override
    public void addDefaults() {
        addSection("Pay2Chat by Niestrat99");

        // Debug section
        addSection("Debug");
        addDefault("debug-mode", false, "Prints additional messages of plugin''s actions in the console.");
        addComment(separator);

        // Chat
        addSection("Chat");
        addComment(
                """
                This section is for when a player is chatting normally.
                """
        );
        addDefault("chat-price", 5.0, "Set it to 0.0 in order to turn off chat this option.");
        addComment(separator);

        // Commands
        addSection("Commands");
        addComment(
                """
                This section is for when a player uses a command.
                It can be very useful if you want to limit the use of some commands for players.
                """
        );
        makeSectionLenient("commands");
        addExample("commands.me", 5.0);
        addComment(separator);

        addSection("World Whitelist");
        addComment(
                """
                This section allows you to manage in which worlds this plugin shall work.
                Make sure you enter the world names properly, as this setting is case sensitive!
                """
        );
        addDefault("world-whitelist", new ArrayList<String>(Arrays.asList(
                "world",
                "world_nether",
                "world_the_end"
        )));
    }

    public static double getCommandPrice(String command) {
        if (configFile.contains("commands." + command)) {
            return configFile.getDouble("commands." + command);
        }
        return 0;
    }

    public static void setCommandPrice(String command, Double newPrice) {
        if (!configFile.contains("commands." + command)) {
            configFile.set("commands." + command, newPrice);
        } else {
            if (newPrice == 0) {
                configFile.remove("commands." + command);
            } else {
                configFile.set("commands." + command, newPrice);
            }
        }
        saveConfig();
        reloadConfig();
    }

    public static @NotNull List<String> getCommands() {
        return configFile.getConfigSection("commands").getKeys(false);
    }

    public static double getChatPrice() {
        return configFile.getDouble("chat-price");
    }

    public static void setChatPrice(Double newPrice) {
        configFile.set("chat-price", newPrice);
        saveConfig();
        //reloadConfig();
    }

    public static boolean debugEnabled() {
        return configFile.getBoolean("debug-mode");
    }

    private static void saveConfig() {
        try {
            configFile.save();
        } catch (Exception e) {
            Main.log(Level.SEVERE, "Something went wrong saving the config file!", Config.class, e);
        }
    }

    public static void addWorld(String world) {
        List<String> whitelist = configFile.getList("world-whitelist");
        if (!whitelist.contains(world)) {
            whitelist.add(world);
        }
        saveConfig();
        reloadConfig();
    }

    public static void removeWorld(String world) {
        List<String> whitelist = configFile.getList("world-whitelist");
        whitelist.remove(world);
        saveConfig();
        reloadConfig();
    }

    public static List<String> getWhitelist() {
        return configFile.getList("world-whitelist");
    }
}
