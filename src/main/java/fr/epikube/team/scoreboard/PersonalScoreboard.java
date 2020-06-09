package fr.epikube.team.scoreboard;

import fr.epikube.team.Epikube;
import fr.epikube.team.data.EpikubePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PersonalScoreboard {

    private Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;

    private int playerCount;
    private String role;

    PersonalScoreboard(Player player) {
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "Epikube");

        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData() {
        EpikubePlayer epikPlayer = EpikubePlayer.get(player);
        playerCount = Bukkit.getOnlinePlayers().size();
        role = epikPlayer.getRole().getBukkitColor() + epikPlayer.getRole().getPrefix();

        /** Envoie de nos packets **/
        for (ScoreboardTeam teams : Epikube.getInstance().getTeams()) {
            ((CraftPlayer) Bukkit.getPlayer(player.getName())).getHandle().playerConnection.sendPacket(teams.createTeam());
        }

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            for (Player player2 : Bukkit.getOnlinePlayers()) {
                ScoreboardTeam team = Epikube.getInstance().getSbTeam(EpikubePlayer.get(player2).getRole());
                if (team != null) {
                    ((CraftPlayer)player1).getHandle().playerConnection.sendPacket(team.addOrRemovePlayer(3, player2.getName()));
                }
            }
        }
    }

    public void setLines(String ip) {
        objectiveSign.setDisplayName("§6§lEpikube Jeux");

        objectiveSign.setLine(0, "§1");
        objectiveSign.setLine(1, ChatColor.GOLD + "» Joueurs: " + ChatColor.GREEN + playerCount);
        objectiveSign.setLine(2, ChatColor.GOLD + "» Pseudo: " + ChatColor.BLUE + player.getName());
        objectiveSign.setLine(3, ChatColor.GOLD + "» Rôle: " + role);
        objectiveSign.setLine(4, "§2");
        objectiveSign.setLine(5, ChatColor.GRAY + "» " + ip);

        objectiveSign.updateLines();
    }

    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }

}
