package fr.epikube.team.gui;

import fr.epikube.team.data.EpikubePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class InteractiveInventory implements Listener {

    private String inventoryName;
    private int size;
    private boolean update;

    public int id;
    public Inventory inventory;
    public EpikubePlayer player;
    public Plugin plugin;

    public InteractiveInventory(int id, String inventoryName, int size) {
        this(id, inventoryName, size, false);
    }

    public InteractiveInventory(int id, String inventoryName, int size, boolean update) {
        this.id = id;
        this.inventoryName = inventoryName;
        this.size = size;
        if (size <= 0)
            throw new IllegalArgumentException("It must be at least 1 line for the inventory you tried to init.");
        if (size > 6)
            throw new IllegalArgumentException("Size of the inventory can't exceed 6 because minecraft accepts only 6 lines max.");
        this.update = update;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return inventoryName;
    }

    public EpikubePlayer getEpikubePlayer() {
        return player;
    }

    public boolean isUpdate() {
        return this.update;
    }

    public void open(Plugin plugin, EpikubePlayer player) {
        this.inventory = Bukkit.createInventory(null, size * 9, inventoryName);
        this.player = player;
        this.plugin = plugin;
        onOpen();
        player.getPlayer().openInventory(this.inventory);
        drawScreen();
        player.getPlayer().updateInventory();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void close() {
        this.player.getPlayer().closeInventory();
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setItem(ItemStack item, int slot) {
        this.inventory.setItem(slot, item);
    }

    public void addItem(ItemStack item) {
        this.inventory.addItem(item);
    }

    public void setItem(ItemStack item, int line, int colomn) {
        setItem(item, (line * 9 - 9) + colomn - 1);
    }

    public void setItemLine(ItemStack item, int line) {
        for (int i = (line * 9 - 9); i < (line * 9); i++)
            setItem(item, i);
    }

    public void clearInventory() {
        this.inventory.clear();
    }

    public void setFont(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++)
            setItem(item, i);
    }

    @EventHandler
    public void onPlayerInventory(InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;
        if (event.getClickedInventory().equals(this.inventory))
            onClick(event.getCurrentItem(), event);
    }

    public abstract void drawScreen();

    public abstract void onOpen();

    public abstract void onClose();

    public abstract void onClick(ItemStack item, InventoryClickEvent event);

    @EventHandler
    public void onPlayerInventory(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player))
            return;
        EpikubePlayer player = EpikubePlayer.get((Player) e.getPlayer());
        if (InteractiveInventoryManager.hasOpenedGui(player)) {
            InteractiveInventoryManager.closePlayer(player);
            onClose();
        }
        if (!InteractiveInventoryManager.isOpened(this)) {
            HandlerList.unregisterAll(this);
        }
    }


}
