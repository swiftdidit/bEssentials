package me.fleeking.bessentials.events;

import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinLeave implements Listener {
    private final bEssentials core;
    public JoinLeave(bEssentials core){
        this.core = core;
    }
    @EventHandler
    public void onJoin(ServerConnectEvent e){
        ProxiedPlayer player = e.getPlayer();



        if(player.hasPermission("bessentials.joinmsg")){
            if(core.getManager().isJoinMsgs()){
                Message.of("&a" + player.getName() + " &ehas joined the game &b("
                        + core.getProxy().getOnlineCount() + "&e/" +
                        core.getProxy().getConfig().getPlayerLimit() + "&b)").broadcast();
            }
        }
    }
    @EventHandler
    public void onLeave(ServerConnectEvent e){
        ProxiedPlayer player = e.getPlayer();

        if(player.hasPermission("bessentials.leavemsg")) {
            if (core.getManager().isJoinMsgs()) {
                Message.of("&c" + player.getName() + " &ehas left the game &b("
                        + core.getProxy().getOnlineCount() + "&e/" +
                        core.getProxy().getConfig().getPlayerLimit() + "&b)").broadcast();
            }
            return;
        }

        if(!core.getManager().ignoring.isEmpty() && core.getManager().isIgnoring(player)){
            core.getManager().saveIgnoredInfo(player);
        }
    }
}
