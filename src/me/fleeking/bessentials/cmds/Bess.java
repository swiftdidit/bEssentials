package me.fleeking.bessentials.cmds;

import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class Bess extends Command {
    private final bEssentials core;
    private final Configuration config;
    public Bess(bEssentials core) {
        super("bess", "bessentials.admin", "bessentials", "bessential");
        this.core = core;
        this.config = bEssentials.getInstance().getFiles().getConfig().get();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.bess")){
            if(args.length == 0){
                Message.of("messages.bessentials-command").send(sender);
            }else if (args[0].equalsIgnoreCase("reload")) {
                    bEssentials.getInstance().getFiles().reloadAllConfigs();
                    Message.of("messages.config-reload").send(sender);
                }else if(args[0].equalsIgnoreCase("version")) {
                Message.of("&6Config Version &f: &9" + config.getInt("config-version") + "-.0_SNAPSHOT").send(sender);
            }else{
                Message.of("messages.bessentials-command").send(sender);
            }
        }else{
            Message.of("messages.permission-denied").send(sender);
        }
    }
}
