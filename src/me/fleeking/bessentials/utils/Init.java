package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
public class Init {
    private final bEssentials core;
    private Config lang;
    private Config config;
    private Config ignored_players;
    public Init(bEssentials core){
        this.core = core;

        lang = new Config("lang.yml", core);
        config = new Config("config.yml", core);
        ignored_players = new Config("ignored_players.yml", core);
    }
    public Config getLang() {
        return lang;
    }
    public Config getConfig() {
        return config;
    }
    public Config getIgnoredYml(){
        return ignored_players;
    }
    public void reloadAllConfigs(){
        bEssentials.getInstance().getFiles().getLang().reloadConfig();
        bEssentials.getInstance().getFiles().getIgnoredYml().reloadConfig();
    }
}
