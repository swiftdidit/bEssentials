package me.fleeking.bessentials.utils;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.*;
import java.util.concurrent.TimeUnit;
public class SocialspyRun implements Runnable {
    private final Plugin plugin;
    private ScheduledTask task;
    private Map<ProxiedPlayer, Set<String>> previousMessages;
    public SocialspyRun(Plugin plugin) {
        this.plugin = plugin;
        this.previousMessages = new HashMap<>();
    }
    public void start() {
        TaskScheduler scheduler = ProxyServer.getInstance().getScheduler();
        task = scheduler.schedule(plugin, this, 0, 2, TimeUnit.SECONDS); // Run every 2 seconds
    }
    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
    @Override
    public void run() {
        for (Map.Entry<ProxiedPlayer, Boolean> entry : bEssentials.getInstance().getManager().socialSpyMap.entrySet()) {
            ProxiedPlayer player = entry.getKey();
            if (entry.getValue()) {
                List<String> messages = bEssentials.getInstance().getManager().socialSpyMessages.get(player);
                if (messages != null && !messages.isEmpty()) {
                    Set<String> previous = previousMessages.getOrDefault(player, new HashSet<>());
                    for (String msg : messages) {
                        if (!previous.contains(msg)) {
                            Message.of("messages.admin.socialspy.ss-prefix").placeholders(
                                            ImmutableMap.of("%info%", msg))
                                    .send(player);
                        }
                        previous.add(msg);
                    }
                    previousMessages.put(player, previous);
                }
            }
        }
    }
}


