package me.fleeking.bessentials.cmds;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ping extends Command implements TabExecutor {
    private final bEssentials core;
    public Ping(bEssentials core) {
        super("ping");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            if (args.length < 1) {
                Message.of("messages.ping.ping-usage").send(sender);
            } else {
                if (args.length >= 1) {

                    String playerName = args[0];
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(playerName);

                    if (target == null || !target.isConnected()) {
                        Message.of("messages.invalid-player").send(sender);
                        return;
                    }

                    int ping = target.getPing();

                    Message.of("messages.ping.players-ping")
                            .placeholders(ImmutableMap.of(
                                    "%player%", target.getName(),
                                    "%ping%", String.valueOf(ping)))
                            .send(sender);
                }
            }
        } else {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (player.hasPermission("bessentials.admin") || player.hasPermission("bessentials.ping")) {
                if (args.length < 1) {
                    int playerPing = player.getPing();

                    Message.of("messages.ping.my-ping")
                            .placeholders(ImmutableMap.of(
                                    "%player%", player.getName(),
                                    "%ping%", String.valueOf(playerPing)))
                            .send(sender);
                } else {
                    if (args.length >= 1) {

                        String playerName = args[0];
                        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(playerName);

                        if (target == null || !target.isConnected()) {
                            Message.of("messages.invalid-player").send(player);
                            return;
                        }

                        int ping = target.getPing();

                        Message.of("messages.ping.players-ping")
                                .placeholders(ImmutableMap.of(
                                        "%player%", target.getName(),
                                        "%ping%", String.valueOf(ping)))
                                .send(sender);
                    }
                }
            } else {
                Message.of("messages.permission-denied").send(sender);
            }
        }
    }
    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        if (args.length >= 1) {
            // Tab completion for the first argument (target player)
            List<String> playerNames = new ArrayList<>();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        }

        return Collections.emptyList();
    }
}
