package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.cmds.*;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommandManager {
    private final bEssentials core;
    public CommandManager(bEssentials core){
        this.core = core;
    }
    public void manageCommands(){
        PluginManager pm = core.getProxy().getPluginManager();

        if(!core.getManager().isHelpCmd()){
            pm.unregisterCommand(new Help(core));
        }
        if(!core.getManager().isLobbyCmd()){
            pm.unregisterCommand(new Lobby(core));
        }
        if(!core.getManager().isStaffChatCmd()){
            pm.unregisterCommand(new Staffchat(core));
        }
        if(!core.getManager().isMsgsCmds()){
            pm.unregisterCommand(new Msg(core));
            pm.unregisterCommand(new Reply(core));
            pm.unregisterCommand(new Socialspy(core));
        }
    }
}
