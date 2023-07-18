package me.fleeking.bessentials;

import me.fleeking.bessentials.cmds.*;
import me.fleeking.bessentials.events.JoinLeave;
import me.fleeking.bessentials.events.MOTD;
import me.fleeking.bessentials.events.StaffchatListener;
import me.fleeking.bessentials.utils.*;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class bEssentials extends Plugin {
    private static bEssentials instance;
    private Manager manager;
    private SocialspyRun socialSpy;
    private Announcements announcements;
    private Init init;
    private CommandManager commandManager;
    public void onEnable(){
        instance = this;
        init = new Init(this);
        Message.of("messages.plugin-enabled").send(getProxy().getConsole());

        manager = new Manager(this);
        socialSpy = new SocialspyRun(this);
        announcements = new Announcements(this, this);

        register();
        commandManager = new CommandManager(this);
        commandManager.manageCommands();

        socialSpy.start();
        announcements.start();
    }
    public void onDisable(){
        Message.of("messages.plugin-disabled").send(getProxy().getConsole());

        manager.cleanUp();
        socialSpy.stop();
        announcements.stop();
    }
    private void register(){
        PluginManager pm = getProxy().getPluginManager();

        pm.registerCommand(this, new Servers(this));
        pm.registerCommand(this, new Plugins(this));
        pm.registerCommand(this, new Help(this));
        pm.registerCommand(this, new Msg(this));
        pm.registerCommand(this, new Lobby(this));
        pm.registerCommand(this, new Reply(this));
        pm.registerCommand(this, new Ping(this));
        pm.registerCommand(this, new Socialspy(this));
        pm.registerCommand(this, new Staffchat(this));
        pm.registerCommand(this, new Bess(this));
        pm.registerCommand(this, new Teleport(this));
        pm.registerCommand(this, new Tpa(this));
        pm.registerCommand(this, new Tpadeny(this));
        pm.registerCommand(this, new Tpaccept(this));
        pm.registerCommand(this, new Tpahere(this));
        pm.registerCommand(this, new Tphere(this));
        pm.registerCommand(this, new AFK(this));

        //TODO: Tab Header and Tab Footer, SetMOTD, set Tab and Footer, maintenance mode

        pm.registerListener(this, new JoinLeave(this));
        pm.registerListener(this, new MOTD(this));
        pm.registerListener(this, new StaffchatListener(this));
    }
    public Init getFiles(){
        return init;
    }
    public Manager getManager(){
        return manager;
    }
    public static bEssentials getInstance(){
        return instance;
    }
}
