package fr.epikube.team.listeners;

import fr.epikube.team.Epikube;
import fr.epikube.team.gui.item.ChooseRoleItem;
import fr.epikube.team.gui.item.ReturnToHubItem;
import fr.epikube.team.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        /** Init scoreboard **/
        Epikube.getInstance().getScoreboardManager().onLogin(player);
        /** Join message **/
        event.setJoinMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " a rejoint le jeu. " + ChatColor.GREEN + "(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")");
        /** Reset entity **/
        player.setWalkSpeed(0.2f); // Marche classique de MC
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        /** Give items **/
        giveItems(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        /** Remove scoreboard packet **/
        Epikube.getInstance().getScoreboardManager().onLogout(player);
    }

    private void giveItems(final Player player) {
        player.getInventory().setItem(0, ChooseRoleItem.roleItem.getItem());
        player.getInventory().setItem(8, ReturnToHubItem.item.getItem());
    }

}
