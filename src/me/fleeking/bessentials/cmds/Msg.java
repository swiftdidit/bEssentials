package me.fleeking.bessentials.cmds;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Msg extends Command implements TabExecutor {
    private final bEssentials core;
    public Msg(bEssentials core) {
        super("msg", "bessentials.admin", "message");
        this.core = core;
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.msg")) {
            if (args.length == 0) {
                Message.of("messages.msg-and-reply.msg-usage").send(sender);
            } else if (args.length >= 2) {
                String targetName = args[0];
                String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(targetName);

                if (target == null || !target.isConnected()) {
                    Message.of("messages.invalid-player").send(sender);
                    return;
                }

                if(core.getManager().isIgnoring(target)){
                    Message.of("messages.msg-and-reply.msg-notsent").placeholders(
                                    ImmutableMap.of("%player%", "" + target.getName()))
                            .send(sender);
                    return;
                }

                core.getManager().messaging.clear();
                core.getManager().messaging.put(sender, target);

                // Message TO
                Message.of("messages.msg-and-reply.msg-to-target")
                        .placeholders(ImmutableMap.of(
                                "%target%", target.getName(),
                                "%message%", message))
                        .send(target);

                // Message FROM
                Message.of("messages.msg-and-reply.msg-from-sender")
                        .placeholders(ImmutableMap.of(
                                "%sender%", sender.getName(),
                                "%message%", message))
                        .send(sender);

                // socialspy
                List<String> targetMessages = core.getManager().socialSpyMessages.getOrDefault(target, new ArrayList<>());
                targetMessages.add(sender.getName() + ": " + message);
                core.getManager().socialSpyMessages.put(target, targetMessages);
            } else {
                Message.of("messages.msg-and-reply.msg-usage").send(sender);
            }
        } else {
            Message.of("messages.permission-denied").send(sender);
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
