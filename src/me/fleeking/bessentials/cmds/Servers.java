package me.fleeking.bessentials.cmds;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class Servers extends Command {
    private final bEssentials core;

    public Servers(bEssentials core) {
        super("status");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.status")) {
            String offline = String.valueOf(Message.of("messages.server-status.offline-status"));
            String online = String.valueOf(Message.of("messages.server-status.online-status"));

            Message.of("messages.server-status.header").send(sender); // Display the header message once

            ProxyServer.getInstance().getServers().forEach((s, serverInfo) -> {
                serverInfo.ping((result, error) -> {
                    String serverName = serverInfo.getName();
                    String status = (error != null) ? offline : online;

                        Message.of("messages.server-status.server-display") // Display the below-header message once below the header
                                .placeholders(ImmutableMap.of("%server%", serverName, "%status%", status))
                                .send(sender);
                });
            });
        } else {
            Message.of("messages.permission-denied").send(sender);
        }
    }



}
