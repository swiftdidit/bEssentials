package me.fleeking.bessentials.utils;

import com.google.common.collect.ImmutableMap;
import me.fleeking.bessentials.bEssentials;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MiscUtil {
    public static void spyPlayer(ProxiedPlayer target) {
        boolean socialSpyStatus = bEssentials.getInstance().getManager().socialSpyMap.getOrDefault(target, false);

        if (socialSpyStatus) {
            bEssentials.getInstance().getManager().socialSpyMap.put(target, false);

            Message.of("messages.admin.socialspy.leave-spy").placeholders(
                            ImmutableMap.of("%player%", "" + target.getName()))
                    .send(target);
        } else {
            bEssentials.getInstance().getManager().socialSpyMap.put(target, true);

            Message.of("messages.admin.socialspy.enter-spy").placeholders(
                            ImmutableMap.of("%player%", "" + target.getName()))
                    .send(target);
        }
    }
    public static void setPlayerAfk(ProxiedPlayer player, boolean afk){
        bEssentials.getInstance().getManager().afk.put(player, afk);
    }
    public static void afkCommand(ProxiedPlayer player) {
        boolean afked = bEssentials.getInstance().getManager().afk.getOrDefault(player, false);

        if (afked) {
            bEssentials.getInstance().getManager().afk.put(player, false);
            if (bEssentials.getInstance().getManager().isBroadcastAfk()) {
                Message.of("messages.player.afk.afk-disabled").send(player);
                Message.of("messages.player.afk.disable-afk-broadcast")
                        .placeholders(ImmutableMap.of("%player%", player.getName()))
                        .broadcast();
            } else {
                Message.of("messages.player.afk.afk-disabled").send(player);
            }
        } else {
            bEssentials.getInstance().getManager().afk.put(player, true);
            if (bEssentials.getInstance().getManager().isBroadcastAfk()) {
                Message.of("messages.player.afk.enable-afk").send(player);
                Message.of("messages.player.afk.enable-afk-broadcast")
                        .placeholders(ImmutableMap.of("%player%", player.getName()))
                        .broadcast();
            } else {
                Message.of("messages.player.afk.enable-afk").send(player);
            }
        }
    }
    public static String toColor(String base){
        return ChatColor.translateAlternateColorCodes('&', base);
    }
}
