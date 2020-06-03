package fr.epikube.team.gui.views;

import fr.epikube.team.data.EpikubePlayer;
import fr.epikube.team.gui.InteractiveInventory;
import fr.epikube.team.roles.Roles;
import fr.epikube.team.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryRoleChoose extends InteractiveInventory {

    public InventoryRoleChoose() {
        super(0, ChatColor.DARK_GRAY + "Choisir son rôle", 1);
    }

    @Override
    public void drawScreen() {
        this.loadItems();
    }

    @Override
    public void onOpen() {}

    @Override
    public void onClose() {}

    @Override
    public void onClick(ItemStack item, InventoryClickEvent event) {
        /** Trois variable principal **/
        final Player clicker = (Player) event.getWhoClicked();
        final EpikubePlayer player = EpikubePlayer.get(clicker);
        final ItemStack actualItem = event.getCurrentItem();
        /** Trois conditions pour éviter les erreurs **/
        if (actualItem == null) return;
        if (!actualItem.hasItemMeta()) return;
        if (!actualItem.getItemMeta().hasDisplayName()) return;
        /** Vérifie le type de click & Que L'inventaire correspond bien **/
        if (event.getInventory().getName().equalsIgnoreCase(this.inventory.getName())) {
            for (Roles roles : Roles.values()) {
                if (actualItem.getItemMeta().getDisplayName().equalsIgnoreCase(roles.getBukkitColor() + roles.getPrefix())) {
                    player.getPlayer().sendMessage(ChatColor.GOLD + "Tu choisis le rôle: " + roles.getBukkitColor() + roles.getPrefix());
                    player.setRole(roles);
                    player.getPlayer().closeInventory();
                }
            }
        }
        event.setCancelled(true);
    }

    private void loadItems() {
        int slot = 0;
        for (Roles roles : Roles.values()) {
            if (roles.equals(Roles.NONE)) continue;
            setItem(createItemByGame(roles), slot);
            slot++;
        }
    }

    private ItemStack createItemByGame(Roles roles) {
        ItemBuilder result = new ItemBuilder(roles.getIcon());
        final String gameName = roles.getBukkitColor() + roles.getPrefix();
        result.setName(gameName);
        // Convert to Bukkit ItemStack
        return result.toItemStack();
    }
}
