package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class Init {
    private final bEssentials core;
    private final Config lang;
    private final Config config;
    private final Config ignored_players;
    private final JedisPoolConfig jedisPoolConfig;
    private final JedisPool pool;
    private final Jedis jedis;
    public Init(bEssentials core){
        this.core = core;

        String host = getConfig().get().getString("redis.host");
        int port = getConfig().get().getInt("redis.port");
        String username = getConfig().get().getString("redis.username");
        String password = getConfig().get().getString("redis.password");

        this.jedisPoolConfig = new JedisPoolConfig();
        this.pool = new JedisPool(jedisPoolConfig, host, port, username, password);
        this.jedis = pool.getResource();

        this.lang = new Config("lang.yml", core);
        this.config = new Config("config.yml", core);
        this.ignored_players = new Config("ignored_players.yml", core);

        jedis.set("config", core.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
        jedis.set("lang", core.getDataFolder().getAbsolutePath() + File.separator + "lang.yml");
        jedis.set("ignored_players", core.getDataFolder().getAbsolutePath() + File.separator + "ignored_players.yml");
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
