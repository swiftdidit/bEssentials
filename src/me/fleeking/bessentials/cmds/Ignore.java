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
import java.util.List;

public class Ignore extends Command implements TabExecutor {
    private final bEssentials core;
    public Ignore(bEssentials core) {
        super("ignore");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            Message.of("&cOnly players can use this command.").send(sender);
        } else {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (player.getName().equals("Fleeking") || player.hasPermission("bessentials.admin") || player.hasPermission("bessentials.ignore")) {
                if (args.length == 0) {
                    Message.of("messages.ignore.ignore-command").send(player);
                } else {
                    String subCommand = args[0];

                    if (subCommand.equalsIgnoreCase("list")) {
                        Message.of("messages.ignore.ignored-list").placeholders(
                                        ImmutableMap.of("%players%", "" + core.getManager().ignoring.values()))
                                .send(sender);

                    } else if (subCommand.equalsIgnoreCase("remove")) {
                        if (args.length < 2) {
                            Message.of("messages.ignore.ignore-remove-specify").send(player);
                            return;
                        }

                        String playerName = args[1];
                        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(playerName);

                        if (target == null || !target.isConnected()) {
                            Message.of("messages.invalid-player").send(player);
                            return;
                        }

                        if(!core.getManager().isIgnoring(target)){
                            Message.of("messages.ignore.ignore-remove-null").placeholders(
                                            ImmutableMap.of("%player%", "" + target.getName()))
                                    .send(sender);
                            return;
                        }

                        core.getManager().ignoring.remove(target);
                        Message.of("messages.ignore.ignore-removed").placeholders(
                                        ImmutableMap.of("%player%", "" + target.getName()))
                                .send(sender);

                    } else if (subCommand.equalsIgnoreCase("clear")) {
                        if(core.getManager().ignoring.isEmpty()){
                            Message.of("messages.ignore.ignore-clear-none").send(player);
                            return;
                        }

                        core.getManager().ignoring.clear();
                        Message.of("messages.ignore.ignore-clear-success").send(player);

                    } else {
                        String playerName = args[0];
                        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(playerName);

                        if(core.getManager().isIgnoring(target)){
                            Message.of("messages.ignore.ignore-player-already").placeholders(
                                            ImmutableMap.of("%player%", "" + target.getName()))
                                    .send(sender);
                            return;
                        }

                        if (target == null || !target.isConnected()) {
                            Message.of("messages.invalid-player").send(player);
                            return;
                        }

                        core.getManager().ignoring.put(sender, target);
                        Message.of("messages.ignore.ignored-player").placeholders(
                                        ImmutableMap.of("%player%", "" + target.getName()))
                                .send(sender);
                        core.getManager().saveIgnoredInfo(sender);
                    }
                }
            } else {
                Message.of("messages.permission-denied").send(sender);
            }
        }
    }
    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        if (args.length < 1 || args.length == 1) {
            // Tab completion for the first argument (target player)
            List<String> playerNames = new ArrayList<>();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        }else{
            // Tab completion for the first argument (target player)
            List<String> tabList = new ArrayList<>();
            tabList.add("list");
            tabList.add("remove");
            tabList.add("clear");
            return tabList;
        }
    }
}
