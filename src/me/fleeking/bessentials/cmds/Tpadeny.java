package me.fleeking.bessentials.cmds;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Iterator;
import java.util.Map;

public class Tpadeny extends Command {
    private final bEssentials core;
    public Tpadeny(bEssentials core) {
        super("tpadeny", "bessentials.admin", "tpdeny", "tpad");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            Message.of("&cOnly players can use this command!").send(sender);
        } else {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (player.hasPermission("bessentials.admin") || player.hasPermission("bessentials.tpadeny")) {
                if (core.getManager().tpa.isEmpty()) {
                    Message.of("messages.player.tpa.tpa-deny-norequest").send(player);
                    return;
                }

                Iterator<Map.Entry<CommandSender, ProxiedPlayer>> iterator = core.getManager().tpa.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<CommandSender, ProxiedPlayer> entry = iterator.next();
                    CommandSender commandSender = entry.getKey();
                    ProxiedPlayer proxiedPlayer = entry.getValue();

                    iterator.remove();

                    Message.of("messages.player.tpa.tpa-denied")
                            .placeholders(ImmutableMap.of(
                                    "%requester%", proxiedPlayer.getName()))
                            .send(commandSender);

                    Message.of("messages.player.tpa.tpa-denied-to-player-denied")
                            .placeholders(ImmutableMap.of(
                                    "%denier%", commandSender.getName()))
                            .send(proxiedPlayer);
                }
            }
        }
    }
}
