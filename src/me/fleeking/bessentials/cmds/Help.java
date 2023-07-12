package me.fleeking.bessentials.cmds;

import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Help extends Command {
    private final bEssentials core;
    public Help(bEssentials core) {
        super("help");
        this.core = core;
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.help")){
            Message.of("messages.help").send(sender);
        }else{
            Message.of("messages.permission-denied").send(sender);
        }
    }
}
