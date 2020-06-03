package fr.epikube.team.listeners;

import fr.epikube.team.data.EpikubePlayer;
import fr.epikube.team.roles.Roles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        final EpikubePlayer player = EpikubePlayer.get(event.getPlayer());

        event.setCancelled(true); // Event cancel

        StringBuilder buffer = new StringBuilder();
        buffer.append(player.getRole().getBukkitColor() + player.getRole().getPrefix()).append(ChatColor.WHITE + " | ");
        buffer.append(player.getPlayer().getName()).append(ChatColor.DARK_GRAY + " » ");
        buffer.append(player.getRole().getBukkitColor() + event.getMessage());
        // Convert to String
        String message = buffer.toString();
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online != null) online.sendMessage(message);
        }
    }
}
