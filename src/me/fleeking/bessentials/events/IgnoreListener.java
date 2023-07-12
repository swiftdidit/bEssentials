package me.fleeking.bessentials.events;

import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class IgnoreListener implements Listener {
    private final bEssentials core;
    public IgnoreListener(bEssentials core) {
        this.core = core;
    }
    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        ProxiedPlayer sender = (ProxiedPlayer) e.getSender();
        String message = e.getMessage();

        //TODO: test if this actually works with another person
        if (sender != null && core.getManager().isIgnoring(sender)) {
            if (message.startsWith("/")) {
                return;
            }
            e.setCancelled(true);
        } else {
            // You can send a message to other players to inform them about the message.
            for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){

                if(core.getManager().ignoring.containsKey(players)){
                    Message.of(message).send(players);
                    return;
                }
            }
        }
    }
}

