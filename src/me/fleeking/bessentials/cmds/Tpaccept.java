package me.fleeking.bessentials.cmds;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Tpaccept extends Command {
    private final bEssentials core;
    public Tpaccept(bEssentials core) {
        super("tpaccept", "bessentials.admin", "tpaaccept");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            Message.of("messages.only-players").send(sender);
        }else{
            if(sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.tpaccept")){
                if(core.getManager().tpa.containsKey(sender)){
                    ProxiedPlayer requester = core.getManager().tpa.remove(sender);

                    if(core.getFiles().getJedis().get("tpaHereTargetName").equals(sender.getName())){
                        core.getFiles().pubJedis("bungee", "tpahere:accepted");
                        Message.of("messages.player.tpa.accepted-teleporting")
                                .placeholders(ImmutableMap.of("%target%", sender.getName()))
                                .send(requester);
                        Message.of("messages.player.tpa.accepted-teleporting-sender")
                                .placeholders(ImmutableMap.of("%sender%", requester.getName()))
                                .send(sender);
                        return;
                    }

                    core.getFiles().pubJedis("bungee", "tpa:accepted");

                    Message.of("messages.player.tpa.accepted-teleporting")
                            .placeholders(ImmutableMap.of("%target%", requester.getName()))
                            .send(sender);
                    Message.of("messages.player.tpa.accepted-teleporting-sender")
                            .placeholders(ImmutableMap.of("%sender%", sender.getName()))
                            .send(requester);
                }else{
                    Message.of("messages.player.tpa.tpa-deny-norequest").send(sender);
                }
            }else{
                Message.of("messages.permission-denied").send(sender);
            }
        }
    }
}
