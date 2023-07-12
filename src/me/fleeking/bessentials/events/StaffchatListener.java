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

        if (core.getManager().inStaffChat(player)) {
            if(message.startsWith("/")){
                return;
            }

            for (ProxiedPlayer staffMember : ProxyServer.getInstance().getPlayers()) {
                if (core.getManager().inStaffChat(staffMember)) {
                    e.setCancelled(true);
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

