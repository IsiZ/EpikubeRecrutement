package fr.epikube.team.gui.item;

import fr.epikube.team.data.EpikubePlayer;
import fr.epikube.team.gui.ClickableItem;
import fr.epikube.team.gui.InteractiveInventoryManager;
import fr.epikube.team.gui.views.InventoryRoleChoose;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ChooseRoleItem extends ClickableItem {

    public static ChooseRoleItem roleItem = new ChooseRoleItem();

    public ChooseRoleItem() {
        super(Material.NETHER_STAR, ChatColor.GOLD + "Choisi ton rôle");
        setDropable(false);
        setMovable(false);
        setClickableFromHotbar(true);
        setClickableFromInventory(true);
    }

    @Override
    public void onUse(EpikubePlayer player, ItemStack item, UseSource useFrom, PlayerInteractEvent interactEvent, InventoryClickEvent inventoryClickEvent) {
        InteractiveInventoryManager.open(player, new InventoryRoleChoose());
    }
}
