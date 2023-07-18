package me.fleeking.bessentials.cmds;

import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import me.fleeking.bessentials.utils.MiscUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AFK extends Command {
    private final bEssentials core;
    public AFK(bEssentials core) {
        super("afk", "bessentials.admin");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            Message.of("messages.only-players").send(sender);
        }else{
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if(sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.afk")){
                MiscUtil.afkCommand(player);
            }else{
                Message.of("messages.permission-denied").send(sender);
            }
        }
    }
}
