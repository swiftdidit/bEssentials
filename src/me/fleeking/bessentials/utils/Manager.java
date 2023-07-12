package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
    private final bEssentials core;
    public Map<CommandSender, ProxiedPlayer> messaging = new HashMap<>();
    public Map<CommandSender, ProxiedPlayer> ignoring = new HashMap<>();
    public HashMap<ProxiedPlayer, Boolean> socialSpyMap = new HashMap<>();
    public Map<ProxiedPlayer, List<String>> socialSpyMessages = new HashMap<>();
    public Map<ProxiedPlayer, Boolean> staffChat = new HashMap<>();
    public Manager(bEssentials core){
        this.core = core;
    }
    public void connectPlayer(ProxiedPlayer player, ServerInfo serverInfo) {
        player.connect(serverInfo);
    }
    public boolean isMessaging(ProxiedPlayer player) {
        return messaging.containsKey(player);
    }
    public boolean isIgnoring(ProxiedPlayer player) {
        return ignoring.containsKey(player);
    }
    public boolean inStaffChat(ProxiedPlayer player){
        return staffChat.containsValue(true);
    }
    public boolean isMotdSet(){
        return core.getFiles().getConfig().get().getBoolean("settings.motd");
    }
    public boolean isAnnouncements(){
        return core.getFiles().getConfig().get().getBoolean("settings.announcements");
    }
    public boolean isJoinMsgs() {
        return core.getFiles().getConfig().get().getBoolean("settings.join-message");
    }
    public boolean isLeaveMsgs() {
        return core.getFiles().getConfig().get().getBoolean("settings.leave-message");
    }
    public void saveIgnoredInfo(CommandSender sender){
        if(ignoring.isEmpty()){
            Message.of("[Config] No ignored players too save").send(core.getProxy().getConsole());
            return;
        }

        ignoring.forEach((commandSender, proxiedPlayer) -> {
            Message.of("[Config] Saved ignored players").send(core.getProxy().getConsole());
            core.getFiles().getIgnoredYml().get().set
                    ("ignored-players.player-list." + commandSender.getName() + ".ignoring", proxiedPlayer.getName());

            core.getFiles().getIgnoredYml().save();
        });
    }
    public void cleanUp(){
        messaging.clear();
    }
}
