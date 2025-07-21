package org.blazepxly.killMessage;

import org.blazepxly.killMessage.command.Listener;
import org.blazepxly.killMessage.command.mainCommand;
import org.blazepxly.killMessage.command.mainCommandTab;
import org.blazepxly.killMessage.command.placeholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class KillMessage extends JavaPlugin implements org.bukkit.event.Listener {

    public final List<UUID> playersActived = new ArrayList<>();
    public final HashMap<String, String> customMessages = new HashMap<>();
    public final HashMap<UUID, String> playerMessageStyles = new HashMap<>();

    @Override
    public void onEnable() {
        System.out.println("Hei!");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        loadMessages();
        loadData();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new placeholderExpansion(this).register();
        }

        getServer().getPluginManager().registerEvents(new Listener(this), this);
        this.getCommand("killmessage").setExecutor(new mainCommand(this));
        this.getCommand("killmessage").setTabCompleter(new mainCommandTab(this));

    }

    @Override
    public void onDisable() {
        System.out.println("Bye!");
    }

    public void loadMessages() {
        saveDefaultConfig();
        customMessages.clear();

        ConfigurationSection messageSection = getConfig().getConfigurationSection("kill-message");

        if (messageSection != null) {
            for (String key : messageSection.getKeys(false)) {
                String message = messageSection.getString(key);

                if (message != null) {
                    customMessages.put(key, message);
                }
            }
            getLogger().info("Berhasil memuat " + customMessages.size() + " kill message.");
        } else {
            getLogger().warning("Tidak ditemukan kill-message di config.yml");
        }
    }

    public void saveData() {
        List<String> uuidStrings = new ArrayList<>();
        for (UUID uuid : playersActived) {
            uuidStrings.add(uuid.toString());
        }
        getConfig().set("data.players-actived", uuidStrings);

        getConfig().set("data.player-styles", null);
        for (Map.Entry<UUID, String> entry : playerMessageStyles.entrySet()) {
            getConfig().set("data.player-styles." + entry.getKey().toString(), entry.getValue());
        }

        saveConfig();
    }

    public void loadData() {
        playersActived.clear();
        playerMessageStyles.clear();
        ConfigurationSection templatesSection = getConfig().getConfigurationSection("kill-message");
        if (templatesSection != null) {
            for (String key : templatesSection.getKeys(false)) {//BELAJAR FOR, INI APAAN DAH
                customMessages.put(key, templatesSection.getString(key));
            }
        }

        for (String uuidStr : getConfig().getStringList("data.players-actived")) {
            playersActived.add(UUID.fromString(uuidStr)); // gapaham
        }

        ConfigurationSection stylesSection = getConfig().getConfigurationSection("data.player-styles");
        if (stylesSection != null) {
            for (String uuidStr : stylesSection.getKeys(false)) {
                playerMessageStyles.put(UUID.fromString(uuidStr), stylesSection.getString(uuidStr));
            }
        }
    }
}
