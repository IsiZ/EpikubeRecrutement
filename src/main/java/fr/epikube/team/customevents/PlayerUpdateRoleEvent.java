package fr.epikube.team.customevents;

import fr.epikube.team.roles.Roles;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUpdateRoleEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Roles role;

    public PlayerUpdateRoleEvent(Player player, Roles roles) {
        this.player = player;
        this.role = roles;
    }

    public Roles getRole() {
        return role;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
