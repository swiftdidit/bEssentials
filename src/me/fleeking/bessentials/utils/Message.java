package me.fleeking.bessentials.utils;

import me.fleeking.bessentials.bEssentials;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Message {
    private String configSec;
    private String parsedString;
    private List<String> parsedList;
    private boolean isString;

    public Message(String configSec) {
        this.configSec = configSec;
        Config langConfig = bEssentials.getInstance().getFiles().getLang();

        if (langConfig.get().contains(configSec) && langConfig.get().get(configSec) instanceof String) {
            isString = true;
            parsedString = toColor(langConfig.get().getString(configSec));
        } else if (langConfig.get().contains(configSec) && langConfig.get().get(configSec) instanceof List) {
            isString = false;
            parsedList = langConfig.get().getStringList(configSec).stream().map(this::toColor).collect(Collectors.toList());
        } else {
            isString = true;
            parsedString = toColor(configSec);
        }
    }
    public Message placeholders(Map<String, String> placeholders){
        if(isString){
            for(String placeholder : placeholders.keySet())
                parsedString = parsedString.replace(placeholder, placeholders.get(placeholder));
        }else {

            parsedList = parsedList.stream().map(string -> {
                for(String placeholder : placeholders.keySet())
                    string = string.replace(placeholder, placeholders.get(placeholder));

                return string;
            }).collect(Collectors.toList());
        }

        return this;
    }
    public static void staffChatPlayer(ProxiedPlayer player){
        boolean staffChatStatus = bEssentials.getInstance().getManager().staffChat.getOrDefault(player, false);

        if (staffChatStatus) {
            bEssentials.getInstance().getManager().staffChat.put(player, false);
            Message.of("messages.admin.staffchat.leave-sc").send(player);
        } else {
            bEssentials.getInstance().getManager().staffChat.put(player, true);
            Message.of("messages.admin.staffchat.enter-sc").send(player);
        }
    }

    public void send(CommandSender sender){
        if(isString){
            sender.sendMessage(parsedString);
        }else {
            for(String str : parsedList)
                sender.sendMessage(str);
        }
    }
    public void broadcast(){
        if(isString){
            bEssentials.getInstance().getProxy().broadcast(new TextComponent(parsedString));
        }else {
            for(String str : parsedList)
                bEssentials.getInstance().getProxy().broadcast(new TextComponent(str));
        }
    }
    public List<String> toList(){
        if(isString)
            return Collections.singletonList(parsedString);
        return parsedList;
    }

    public String toString(){
        if(isString)
            return parsedString;
        else return parsedList.toString();
    }

    public static Message of(String message){
        return new Message(message);
    }

    private String toColor(String base){
        return ChatColor.translateAlternateColorCodes('&', base);
    }
}
