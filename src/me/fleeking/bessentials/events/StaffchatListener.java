package me.fleeking.bessentials.events;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class StaffchatListener implements Listener {
    private final bEssentials core;
    public StaffchatListener(bEssentials core) {
        this.core = core;
    }
    @EventHandler
    public void onChatEvent(ChatEvent e) {
        ProxiedPlayer player = (ProxiedPlayer) e.getSender();
        String message = e.getMessage();

        //Prefix quick chat.
        if (message.startsWith("!")) {
            if (core.getManager().inStaffChat(player)) {
                e.setMessage(message.substring(1)); // Remove the "!" prefix
            } else {
                e.setCancelled(true);
                for (ProxiedPlayer staffMember : ProxyServer.getInstance().getPlayers()) {
                        Message.of("messages.admin.staffchat.sc-prefix")
                                .placeholders(ImmutableMap.of(
                                        "%player%", player.getName(),
                                        "%message%", message.substring(1))) // Remove the "!" prefix
                                .send(staffMember);
                }
            }
        } else {
            // In staff chat.
            if (core.getManager().inStaffChat(player)) {
                if (message.startsWith("/")) {
                    return;
                }
                e.setCancelled(true);
                for (ProxiedPlayer staffMember : ProxyServer.getInstance().getPlayers()) {
                    if (core.getManager().inStaffChat(staffMember)) {
                        Message.of("messages.admin.staffchat.sc-prefix")
                                .placeholders(ImmutableMap.of(
                                        "%player%", player.getName(),
                                        "%message%", message))
                                .send(staffMember);
                    }
                }
            }
        }
    }
}

