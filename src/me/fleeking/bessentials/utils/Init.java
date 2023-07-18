package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
import redis.clients.jedis.*;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class Init {
    private final bEssentials core;
    private final Config lang;
    private final Config config;
    private final Config ignored_players;
    private final JedisPool pool;
    private final Jedis jedis;
    public Init(bEssentials core){
        this.core = core;

        this.lang = new Config("lang.yml", core);
        this.config = new Config("config.yml", core);
        this.ignored_players = new Config("ignored_players.yml", core);

        String host = getConfig().get().getString("redis.host");
        String password = getConfig().get().getString("redis.pass");
        int port = getConfig().get().getInt("redis.port");
        int timeout = getConfig().get().getInt("redis.timeout");

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        this.pool = new JedisPool(poolConfig, host, port, timeout, password);

        this.jedis = pool.getResource();

        jedis.set("config", core.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
        jedis.set("lang", core.getDataFolder().getAbsolutePath() + File.separator + "lang.yml");
        jedis.set("ignored_players", core.getDataFolder().getAbsolutePath() + File.separator + "ignored_players.yml");
        jedis.set("host", getConfig().get().getString("redis.host"));
        jedis.set("port", String.valueOf(getConfig().get().getInt("redis.port")));
    }
    public void pubJedis(String channel, String message) {
        try (Jedis jedis = pool.getResource()) {
            jedis.publish(channel, message);
            System.out.println(channel + " message published!");
        }
    }
    public Jedis getJedis(){
        return this.jedis;
    }
    public Config getLang() {
        return this.lang;
    }
    public Config getConfig() {
        return this.config;
    }
    public Config getIgnoredYml(){
        return this.ignored_players;
    }
    public void reloadAllConfigs(){
        bEssentials.getInstance().getFiles().getLang().reloadConfig();
        bEssentials.getInstance().getFiles().getIgnoredYml().reloadConfig();
    }
}
