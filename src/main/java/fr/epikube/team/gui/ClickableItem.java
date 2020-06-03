package fr.epikube.team.gui;

import fr.epikube.team.Epikube;
import fr.epikube.team.data.EpikubePlayer;
import fr.epikube.team.utils.ItemBuilder;
import fr.epikube.team.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public abstract class ClickableItem implements Listener {

    private static final String UUID_TAG = "epikube.uuid";

    private ItemStack item;
    private UUID uuid;
    private String name;
    private boolean movable = false;
    private boolean dropable = false;
    private boolean clickableFromInventory = true;
    private boolean clickableFromHotbar = true;

    public ClickableItem(Material mat, String name) {
        this(new ItemStack(mat), name);
    }

    public ClickableItem(ItemBuilder item, String name) {
        this(item.toItemStack(), name);
    }

    public ClickableItem(ItemStack item, String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.item = Utils.saveStringToItemStack(item, UUID_TAG, uuid.toString());
        Bukkit.getServer().getPluginManager().registerEvents(this, Epikube.getInstance());
    }

    public void give(EpikubePlayer player, int slot) {
        player.getPlayer().getInventory().setItem(slot, getItem(player));
    }

    public void remove(EpikubePlayer player) {
        for (int i = 0; i < 36; i++) {
            ItemStack item = player.getPlayer().getInventory().getItem(i);
            if (Utils.getStringFromItemStack(item, UUID_TAG, "null").equals(this.uuid.toString())) {
                player.getPlayer().getInventory().setItem(i, new ItemStack(Material.AIR));
            }
        }
    }

    public ShapedRecipe getShapedRecipe() {
        return new ShapedRecipe(/*new NamespacedKey(Ovalyon.getInstance(), uuid.toString()),*/ getItem());
    }

    public ItemStack getItem() {
        ItemStack item = this.item.clone();
        ItemMeta iMeta = item.getItemMeta();
        iMeta.setDisplayName(name);
        item.setItemMeta(iMeta);
        return item;
    }

    public ItemStack getItem(EpikubePlayer player) {
        ItemStack playerItem = this.item.clone();
        ItemMeta iMeta = playerItem.getItemMeta();
        iMeta.setDisplayName(name);
        playerItem.setItemMeta(iMeta);
        return playerItem;
    }

    public abstract void onUse(EpikubePlayer player, ItemStack item, UseSource useFrom, PlayerInteractEvent interactEvent, InventoryClickEvent inventoryClickEvent);

    @EventHandler
    public void onDropItem(PlayerDropItemEvent dropItemEvent) {
        if (dropable || dropItemEvent.getItemDrop() == null)
            return;
        if (Utils.getStringFromItemStack(dropItemEvent.getItemDrop().getItemStack(), UUID_TAG, "null").equals(this.uuid.toString()))
            dropItemEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent interactEvent) {
        if (!clickableFromHotbar || interactEvent.getAction() == null || interactEvent.getItem() == null || interactEvent.getPlayer() == null)
            return;
        if (Utils.getStringFromItemStack(interactEvent.getItem(), UUID_TAG, "null").equals(this.uuid.toString()))
            onUse(EpikubePlayer.get((Player) interactEvent.getPlayer()), interactEvent.getItem(), UseSource.HOTBAR, interactEvent, null);
    }

    @EventHandler
    public void onCraft(CraftItemEvent craftEvent) {
        if (Utils.getStringFromItemStack(craftEvent.getRecipe().getResult(), UUID_TAG, "null").equals(this.uuid.toString())) {
            craftEvent.setCurrentItem(getItem(EpikubePlayer.get((Player) craftEvent.getWhoClicked())));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getClickedInventory() == null || inventoryClickEvent.getCurrentItem() == null || inventoryClickEvent.getCurrentItem().getType() == Material.AIR || inventoryClickEvent.getWhoClicked() == null)
            return;
        if (Utils.getStringFromItemStack(inventoryClickEvent.getCurrentItem(), UUID_TAG, "null").equals(this.uuid.toString())) {
            if (clickableFromInventory)
                onUse(EpikubePlayer.get((Player) inventoryClickEvent.getWhoClicked()), inventoryClickEvent.getCurrentItem(), UseSource.INVENTORY, null, inventoryClickEvent);
            if (!movable)
                inventoryClickEvent.setCancelled(true);
        }
    }

    public enum UseSource {
        INVENTORY(),
        HOTBAR();
    }

    public void setClickableFromHotbar(boolean clickableFromHotbar) {
        this.clickableFromHotbar = clickableFromHotbar;
    }

    public boolean isClickableFromHotbar() {
        return clickableFromHotbar;
    }

    public void setClickableFromInventory(boolean clickableFromInventory) {
        this.clickableFromInventory = clickableFromInventory;
    }

    public boolean isClickableFromInventory() {
        return clickableFromInventory;
    }

    public void setDropable(boolean dropable) {
        this.dropable = dropable;
    }

    public boolean isDropable() {
        return dropable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isMovable() {
        return movable;
    }
}
