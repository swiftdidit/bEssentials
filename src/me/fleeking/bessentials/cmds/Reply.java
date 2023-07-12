package me.fleeking.bessentials.cmds;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reply extends Command {
    private final bEssentials core;
    public Reply(bEssentials core) {
        super("reply", null, "r");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            Message.of("&cOnly players can use this command!").send(sender);
        }else{
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (args.length == 0) {
                Message.of("messages.msg-and-reply.reply-usage").send(sender);
            } else if (args.length >= 1) {
                String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));

                if(core.getManager().isMessaging(player)){
                    core.getManager().messaging.forEach((proxiedPlayer, proxiedPlayer2) -> {
                        //Message TO
                        Message.of("messages.msg-and-reply.msg-to-target")
                                .placeholders(ImmutableMap.of(
                                        "%target%", proxiedPlayer2.getName(),
                                        "%message%", message))
                                .send(proxiedPlayer);

                        // Message FROM
                        Message.of("messages.msg-and-reply.msg-from-sender")
                                .placeholders(ImmutableMap.of(
                                        "%sender%", sender.getName(),
                                        "%message%", message))
                                .send(proxiedPlayer2);

                        // socialspy
                        List<String> targetMessages = core.getManager().socialSpyMessages.getOrDefault(proxiedPlayer2, new ArrayList<>());
                        targetMessages.add(sender.getName() + ": " + message);
                        core.getManager().socialSpyMessages.put(proxiedPlayer2, targetMessages);
                    });
                    return;
                }

                Message.of("messages.msg-and-reply.reply-no-convo").send(player);
            }
        }
    }
}
