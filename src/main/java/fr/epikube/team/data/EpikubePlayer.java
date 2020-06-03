package fr.epikube.team.data;

import fr.epikube.team.customevents.PlayerUpdateRoleEvent;
import fr.epikube.team.roles.Roles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author IsiZ_
 */
public class EpikubePlayer {

    private static Map<Player, EpikubePlayer> players;

    static {
        players = new HashMap<>();
    }

    private Player player;
    private String name;
    private String uuid;
    private Roles role;

    /**
     * Constructor data
     * @param player
     */
    public EpikubePlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId().toString();
        this.name = player.getName();
        this.role = Roles.NONE;
        // En dernier
        players.put(player, this);
    }

    public void setRole(Roles role) {
        // CustomEvent
        PlayerUpdateRoleEvent event = new PlayerUpdateRoleEvent(player, role);
        Bukkit.getPluginManager().callEvent(event);
        // Update role
        this.role = role;
    }

    public Roles getRole() {
        return role;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    /**
     * Get the Epikube player map.
     * @param player
     * @return
     */
    public static EpikubePlayer get(final Player player) {
        if (players.get(player) != null) {
            return players.get(player);
        } else {
            return new EpikubePlayer(player);
        }
    }
}
