package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.config.Configuration;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class Announcements implements Runnable {
    private final Plugin plugin;
    private final bEssentials core;
    private Configuration lang;
    private Configuration config;
    private ScheduledTask task;
    private int currentIndex = 0;
    public Announcements(Plugin plugin, bEssentials core) {
        this.plugin = plugin;
        this.core = core;
        this.lang = bEssentials.getInstance().getFiles().getLang().get();
        this.config = bEssentials.getInstance().getFiles().getConfig().get();
    }
    public void start() {
        if(core.getManager().isAnnouncements()){
            TaskScheduler scheduler = ProxyServer.getInstance().getScheduler();
            int seconds = config.getInt("settings.announcement-interval");
            int delay = config.getInt("settings.announcement-delay");
            task = scheduler.schedule(plugin, this, delay, seconds, TimeUnit.SECONDS);
        }
    }
    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
    @Override
    public void run() {
        Collection<String> announcements = lang.getStringList("messages.announcements");
        int totalAnnouncements = announcements.size();

        if (totalAnnouncements == 0) {
            return;
        }

        String announcement = announcements.toArray(new String[0])[currentIndex];
        currentIndex = (currentIndex + 1) % totalAnnouncements;

        Message.of(announcement).broadcast();
    }
}
