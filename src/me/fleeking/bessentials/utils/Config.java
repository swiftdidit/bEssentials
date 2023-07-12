package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.*;
import java.nio.file.Files;
public class Config {
    private Configuration config = null;
    private File configFile = null;
    private final String path;
    private final bEssentials main;
    public Config(String path, bEssentials main) {
        this.path = path;
        this.main = main;
        this.configFile = new File(main.getDataFolder(), path);
        this.config = loadConfig();
        saveDefault();
    }
    private Configuration loadConfig() {
        if (!configFile.exists()) {
            try {
                main.getDataFolder().mkdir();
                Files.copy(main.getResourceAsStream(path), configFile.toPath());
            } catch (IOException e) {
                main.getLogger().severe("Error creating default configuration file: " + path);
                e.printStackTrace();
            }
        }

        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            main.getLogger().severe("Error loading configuration file: " + path);
            e.printStackTrace();
        }

        return null;
    }
    public Configuration get() {
        if (config == null) {
            config = loadConfig();
        }
        return config;
    }
    public void save() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
        } catch (IOException e) {
            main.getLogger().severe("Error saving configuration file: " + path);
            e.printStackTrace();
        }
    }
    public void reloadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            main.getLogger().info(configFile.getName() + " reloaded successfully.");
        } catch (IOException e) {
            main.getLogger().severe("Failed to reload configs!");
            e.printStackTrace();
        }
    }
    private void saveDefault() {
        if (!configFile.exists()) {
            try (InputStream inputStream = main.getResourceAsStream(path)) {
                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath());
                }
            } catch (IOException e) {
                main.getLogger().severe("Error creating default configuration file: " + path);
                e.printStackTrace();
            }
        }
    }
}


