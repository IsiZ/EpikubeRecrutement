package fr.epikube.team.roles;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Roles {

    WIZARD(1, "Sorcier", "DARK_RED", Material.STICK),
    MAGE(2, "Mage", "GOLD", Material.BLAZE_ROD),
    THIEF(3, "Voleur", "DARK_GREEN", Material.GOLD_HOE),
    // Ce role devra toujours être en dernier ID.
    NONE(4, "Aucune", "WHITE", Material.AIR);

    private int id;
    private String prefix;
    private String bukkitColor;
    private Material icon;

    Roles(int id, String prefix, String bukkitColor, Material icon) {
        this.id = id;
        this.prefix = prefix;
        this.bukkitColor = bukkitColor;
        this.icon = icon;
    }

    public String getPrefix() {
        return prefix;
    }

    public Material getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public ChatColor getBukkitColor() {
        return ChatColor.valueOf(bukkitColor);
    }

}
