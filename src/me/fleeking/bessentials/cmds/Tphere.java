package me.fleeking.bessentials.cmds;

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

public class Tphere extends Command implements TabExecutor {
    private final bEssentials core;

    public Tphere(bEssentials core) {
        super("tphere", "bessentials.admin", "tph");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            Message.of("messages.only-players").send(sender);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.tphere")) {
            switch(args.length){
                case 1:
                    String targetName = args[0];

                    ProxiedPlayer target = core.getProxy().getPlayer(targetName);

                    if (target == null || !target.isConnected()) {
                        Message.of("messages.invalid-player").send(player);
                        return;
                    }

                    core.getFiles().pubJedis("bungee", "teleport:here");
                    core.getFiles().getJedis().set("tphereTargetName", target.getName());
                    break;

                default:
                    Message.of("messages.admin.teleport.usage").send(player);
            }
        } else {
            Message.of("messages.permission-denied").send(sender);
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            List<String> playerNames = new ArrayList<>();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        }

        return Collections.emptyList();
    }
}