package fr.epikube.team;

import fr.epikube.team.listeners.PlayerChatListener;
import fr.epikube.team.listeners.PlayerConnectListener;
import fr.epikube.team.listeners.PlayerFoodListener;
import fr.epikube.team.roles.Roles;
import fr.epikube.team.scoreboard.ScoreboardManager;
import fr.epikube.team.scoreboard.ScoreboardTeam;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Epikube extends JavaPlugin {

    public static Epikube instance;

    private List<ScoreboardTeam> teams = new ArrayList<>();
    private ScoreboardManager scoreboardManager;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onEnable() {
        instance = this;
        // Executors pool
        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);
        // Listeners
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerConnectListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerFoodListener(), this);
        // Scoreboard
        this.scoreboardManager = new ScoreboardManager();
        // Teams added
        for (Roles role : Roles.values()) {
            teams.add(new ScoreboardTeam(role));
        }
    }

    @Override
    public void onDisable() {
        this.scoreboardManager.onDisable();
    }

    /**
     * Get the role
     * @param role
     * @return
     */
    public ScoreboardTeam getSbTeam(Roles role) {
        for (ScoreboardTeam team : getTeams()) {
            if (team.getRole().equals(role))
                return team;
        }
        return null;
        //return teams.stream().filter(t -> t.equals(rank)).findFirst().get();
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public List<ScoreboardTeam> getTeams() {
        return teams;
    }

    public static Epikube getInstance() {
        return instance;
    }
}
