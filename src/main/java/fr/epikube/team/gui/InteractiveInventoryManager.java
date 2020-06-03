package fr.epikube.team.gui;

import fr.epikube.team.Epikube;
import fr.epikube.team.data.EpikubePlayer;

import java.util.HashMap;
import java.util.Map;

public class InteractiveInventoryManager {

    protected static Map<String, InteractiveInventory> openGuis = new HashMap<>();

    private InteractiveInventoryManager() {
        throw new IllegalStateException("GuiManager.class hasn't to be instantiated.");
    }

    public static InteractiveInventory open(EpikubePlayer player, InteractiveInventory gui) {
        openPlayer(player, gui);
        if (gui.isUpdate())
            new InteractiveInventoryTask(Epikube.getInstance(), player, gui).runTaskTimer(Epikube.getInstance(), 0, 20);
        else {
            gui.open(Epikube.getInstance(), player);
        }
        return gui;
    }

    private static void openPlayer(EpikubePlayer player, InteractiveInventory gui) {
        if (openGuis.containsKey(player.getName()))
            openGuis.remove(player.getName());
        openGuis.put(player.getName(), gui);
    }

    public static void closePlayer(EpikubePlayer player) {
        if (openGuis.containsKey(player.getName()))
            openGuis.remove(player.getName());
    }

    public static boolean hasOpenedGui(EpikubePlayer player) {
        if (openGuis.containsKey(player.getName()))
            return true;
        return false;
    }

    public static boolean isOpened(InteractiveInventory clas) {
        for (InteractiveInventory cla : openGuis.values()) {
            if (cla.getId() == clas.getId())
                return true;
        }
        return false;
    }


}
