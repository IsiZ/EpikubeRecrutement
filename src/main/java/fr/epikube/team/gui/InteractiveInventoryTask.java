package fr.epikube.team.gui;

import fr.epikube.team.customevents.InteractiveInventoryUpdateEvent;
import fr.epikube.team.data.EpikubePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class InteractiveInventoryTask extends BukkitRunnable {

    private final Plugin plugin;
    private final EpikubePlayer player;
    private final InteractiveInventory gui;

    public InteractiveInventoryTask(Plugin plugin, EpikubePlayer player, InteractiveInventory gui) {
        this.plugin = plugin;
        this.player = player;
        this.gui = gui;
        gui.open(plugin, player);
    }

    @Override
    public void run() {
        if (!gui.getInventory().getViewers().contains(this.player.getPlayer())) {
            this.cancel();
            return;
        }

        this.plugin.getServer().getPluginManager().callEvent(new InteractiveInventoryUpdateEvent(this.player, this.gui, false));
        this.gui.drawScreen();
    }


}
