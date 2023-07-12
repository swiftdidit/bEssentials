package me.fleeking.bessentials.cmds;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.stream.Collectors;

public class Plugins extends Command {
    private final bEssentials core;

    public Plugins(bEssentials core) {
        super("plugins");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("bessentials.admin") || sender.hasPermission("bessentials.plugins")){
            PluginManager pm = ProxyServer.getInstance().getPluginManager();

            String comma = core.getFiles().getLang().get().getString("messages.plugins.plugin-comma");
            String plugins = pm.getPlugins().stream()
                    .map(plugin -> "" + plugin.getDescription().getName())
                    .collect(Collectors.joining(comma));
            int pluginCount = pm.getPlugins().size();

            Message.of("messages.plugins.plugin-list")
                    .placeholders(ImmutableMap.of(
                            "%plugins%", plugins,
                            "%count%", String.valueOf(pluginCount)))
                    .send(sender);
        }else{
            Message.of("messages.permission-denied").send(sender);
        }
    }
}
