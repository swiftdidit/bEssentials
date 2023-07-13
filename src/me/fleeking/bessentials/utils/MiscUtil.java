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
    public static String toColor(String base){
        return ChatColor.translateAlternateColorCodes('&', base);
    }
}
