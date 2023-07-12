package me.fleeking.bessentials.cmds;

import me.fleeking.bessentials.bEssentials;
import me.fleeking.bessentials.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Lobby extends Command {
    private final bEssentials core;
    public Lobby(bEssentials core) {
        super("lobby", null, "hub");
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            Message.of("messages.only-players").send(sender);
        }else{
            ProxiedPlayer player = (ProxiedPlayer) sender;
              ServerInfo lobby = ProxyServer.getInstance().getServerInfo(core.getFiles().getConfig().get().getString("lobby.lobby-name"));

            try{
                if(lobby.getPlayers().contains(player)){
                    Message.of("messages.lobby.already-in-lobby").send(player);
                    return;
                }

                core.getManager().connectPlayer(player, lobby);
                Message.of("messages.lobby.lobby-connect").send(player);
            }catch(Exception e){
                Message.of("messages.lobby.connect-error").send(player);
                e.printStackTrace();
            }
        }
    }
}
