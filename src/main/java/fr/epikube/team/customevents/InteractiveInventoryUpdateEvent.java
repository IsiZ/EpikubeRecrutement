package fr.epikube.team.customevents;

import fr.epikube.team.data.EpikubePlayer;
import fr.epikube.team.gui.InteractiveInventory;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InteractiveInventoryUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private EpikubePlayer player;
    private InteractiveInventory gui;

    public InteractiveInventoryUpdateEvent(EpikubePlayer player, InteractiveInventory gui, boolean who) {
        super(who);
        this.player = player;
        this.gui = gui;
    }

    public InteractiveInventory getGui() {
        return gui;
    }

    public EpikubePlayer getViewer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
