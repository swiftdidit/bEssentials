package me.fleeking.bessentials.events;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class MOTD implements Listener {
    private final bEssentials core;
    public MOTD(bEssentials core){
        this.core = core;
    }
    @EventHandler
    public void onConnect(ServerConnectedEvent e) {
        ProxiedPlayer player = e.getPlayer();
        ServerInfo serverInfo = e.getServer().getInfo();

        if(core.getManager().isMotdSet()) {
            String serverName = serverInfo.getName();
            @Deprecated
            String serverVersion = ProxyServer.getInstance().getGameVersion();
            int globalCount = ProxyServer.getInstance().getOnlineCount();
            int serverCount = serverInfo.getPlayers().size();

            int globalMax = ProxyServer.getInstance().getConfig().getPlayerLimit();
            String globalMaxPlayers = String.valueOf(globalMax);
            if(globalMax == -1){
                globalMaxPlayers = "1";
            }

            LocalDateTime now = LocalDateTime.now();
            DayOfWeek dayOfWeek = now.getDayOfWeek();
            String dayOfWeekName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String time = now.format(DateTimeFormatter.ofPattern("hh:mm a"));

            ImmutableMap.Builder<String, String> placeholdersBuilder = ImmutableMap.builder();
            placeholdersBuilder.put("%player%", player.getName());
            placeholdersBuilder.put("%server_name%", serverName);
            placeholdersBuilder.put("%server_version%", serverVersion);
            placeholdersBuilder.put("%global_online_count%", String.valueOf(globalCount));
            placeholdersBuilder.put("%server_online_count%", String.valueOf(serverCount));
            placeholdersBuilder.put("%max_global_count%", (globalMax == -1) ? globalMaxPlayers : String.valueOf(globalMax));
            placeholdersBuilder.put("%day%", dayOfWeekName);
            placeholdersBuilder.put("%time%", time);

            Message.of("messages.motd")
                    .placeholders(placeholdersBuilder.build())
                    .send(player);
        }
    }
}
