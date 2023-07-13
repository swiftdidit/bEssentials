package me.fleeking.bessentials.events;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinLeave implements Listener {
    private final bEssentials core;
    public JoinLeave(bEssentials core){
        this.core = core;
    }
    @EventHandler
    public void onJoin(ServerConnectedEvent e){
        ProxiedPlayer player = e.getPlayer();
        ServerInfo serverInfo = e.getServer().getInfo();

            if(core.getManager().isJoinMsgs()){
                String serverName = serverInfo.getName();
                int globalCount = ProxyServer.getInstance().getOnlineCount();
                int serverCount = serverInfo.getPlayers().size();
                serverCount++;

                int globalMax = ProxyServer.getInstance().getConfig().getPlayerLimit();
                String globalMaxPlayers = String.valueOf(globalMax);
                if(globalMax == -1){
                    globalMaxPlayers = "1";
                }

                Message.of("messages.join-message")
                        .placeholders(ImmutableMap.of(
                                "%player%", player.getName(),
                                "%server_name%", serverName,
                                "%global_online_count%", String.valueOf(globalCount),
                                "%max_global_count%", (globalMax == -1) ? globalMaxPlayers : String.valueOf(globalMax),
                                "%server_online_count%", String.valueOf(serverCount)))
                        .broadcast();
        }
    }
    @EventHandler
    public void onLeave(PlayerDisconnectEvent e){
        ProxiedPlayer player = e.getPlayer();

            if (core.getManager().isJoinMsgs()) {
                int globalCount = ProxyServer.getInstance().getOnlineCount();

                int globalMax = ProxyServer.getInstance().getConfig().getPlayerLimit();
                String globalMaxPlayers = String.valueOf(globalMax);
                if(globalMax == -1){
                    globalMaxPlayers = "1";
                }
                globalCount--;

                Message.of("messages.leave-message")
                        .placeholders(ImmutableMap.of(
                                "%player%", player.getName(),
                                "%global_online_count%", String.valueOf(globalCount),
                                "%max_global_count%", (globalMax == -1) ? globalMaxPlayers : String.valueOf(globalMax)))
                        .broadcast();
            }
    }
}
