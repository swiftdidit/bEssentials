package me.fleeking.bessentials.cmds;

import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Staffchat extends Command {
    private final bEssentials core;
    public Staffchat(bEssentials core) {
        super("staffchat", "bessentials.admin", "sc");
        this.core = core;
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            Message.of("messages.only-players").send(sender);
        }else{
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(player.hasPermission("bessentials.admin") || player.hasPermission("bessentials.staffchat")){

                Message.staffChatPlayer(player);
            }else{
                Message.of("messages.permission-denied").send(sender);
            }
        }
    }
}
