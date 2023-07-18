package me.fleeking.bessentials.cmds;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Tpa extends Command implements TabExecutor {
    private final bEssentials core;

    public Tpa(bEssentials core) {
        super("tpa", "bessentials.admin");
        this.core = core;
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            Message.of("messages.only-players").send(sender);
        } else {
            if (sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.tpa")) {
                switch (args.length) {
                    case 1:
                        String targetName = args[0];

                        ProxiedPlayer target = core.getProxy().getPlayer(targetName);

                        if (core.getManager().tpa.containsKey(target)) {
                            Message.of("messages.player.tpa.already-sent").send(sender);
                            return;
                        }

                        if (target == null || !target.isConnected()) {
                            Message.of("messages.invalid-player").send(sender);
                            return;
                        }

                        core.getManager().tpa.put(sender, target);
                        core.getFiles().pubJedis("bungee", "tpa:sent");
                        core.getFiles().getJedis().set("tpaTargetName", target.getName());

                        scheduleExpirationTask(sender, target);
                        break;

                    default:
                        Message.of("messages.player.tpa.usage").send(sender);
                }
            } else {
                Message.of("messages.permission-denied").send(sender);
            }
        }
    }
    private void scheduleExpirationTask(CommandSender sender, ProxiedPlayer target) {
        TpaExpirationTask expirationTask = new TpaExpirationTask(core, sender, target);
        expirationTask.start();
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
class TpaExpirationTask {
    private final bEssentials core;
    private final CommandSender sender;
    private final ProxiedPlayer target;
    private final long expirationTime;
    private boolean executed;

    public TpaExpirationTask(bEssentials core, CommandSender sender, ProxiedPlayer target) {
        this.core = core;
        this.sender = sender;
        this.target = target;
        this.expirationTime = System.currentTimeMillis() + core.getFiles().getConfig().get().getInt("settings.tpa-expiretime") * 1000L;
        this.executed = false;
    }

    public void start() {
        core.getProxy().getScheduler().schedule(core, this::checkExpiration, 1, 1, TimeUnit.SECONDS);
    }

    private void checkExpiration() {
        if (!executed && System.currentTimeMillis() >= expirationTime) {
            executed = true;

            Iterator<Map.Entry<CommandSender, ProxiedPlayer>> iterator = core.getManager().tpa.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<CommandSender, ProxiedPlayer> entry = iterator.next();
                CommandSender sender = entry.getKey();
                ProxiedPlayer target = entry.getValue();

                iterator.remove();

                Message.of("messages.player.tpa.request-expired")
                        .placeholders(ImmutableMap.of("%target%", target.getName()))
                        .send(sender);
                Message.of("messages.player.tpa.player-request-expired")
                        .placeholders(ImmutableMap.of("%sender%", sender.getName()))
                        .send(target);
            }
        }
    }
}
