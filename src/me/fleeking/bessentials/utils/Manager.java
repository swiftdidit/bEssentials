package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class Manager {
    private final bEssentials core;
    public Map<CommandSender, ProxiedPlayer> messaging = new HashMap<>();
    public HashMap<ProxiedPlayer, Boolean> socialSpyMap = new HashMap<>();
    public Map<ProxiedPlayer, List<String>> socialSpyMessages = new HashMap<>();
    public Map<ProxiedPlayer, Boolean> staffChat = new HashMap<>();
    public Map<CommandSender, ProxiedPlayer> tpa = new HashMap<>();
    public Map<ProxiedPlayer, Boolean> afk = new HashMap<>();
    public Manager(bEssentials core){
        this.core = core;
    }
    public void connectPlayer(ProxiedPlayer player, ServerInfo serverInfo) {
        player.connect(serverInfo);
    }
    public boolean isMessaging(ProxiedPlayer player) {
        return messaging.containsKey(player);
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
    public boolean isHelpCmd() {
        return core.getFiles().getConfig().get().getBoolean("settings.help-command");
    }
    public boolean isLobbyCmd() {
        return core.getFiles().getConfig().get().getBoolean("settings.lobby-command");
    }
    public boolean isStaffChatCmd() {
        return core.getFiles().getConfig().get().getBoolean("settings.staffchat-command");
    }
    public boolean isMsgsCmds() {
        return core.getFiles().getConfig().get().getBoolean("settings.messagesystem-commands");
    }
    public void cleanUp(){
        messaging.clear();
    }

    public boolean isBroadcastAfk() {
        return core.getFiles().getConfig().get().getBoolean("settings.broadcast-afk");
    }
}
