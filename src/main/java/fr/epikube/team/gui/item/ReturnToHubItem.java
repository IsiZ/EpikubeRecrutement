package fr.epikube.team.gui.item;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.epikube.team.Epikube;
import fr.epikube.team.data.EpikubePlayer;
import fr.epikube.team.gui.ClickableItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ReturnToHubItem extends ClickableItem {

    public static List<EpikubePlayer> confirmPlayer = new ArrayList<>();

    public static ReturnToHubItem item = new ReturnToHubItem();

    public ReturnToHubItem() {
        super(Material.BED, ChatColor.RED + "Retour au Hub");
        setDropable(false);
        setMovable(false);
        setClickableFromHotbar(true);
        setClickableFromInventory(true);
    }

    @Override
    public void onUse(EpikubePlayer player, ItemStack item, UseSource useFrom, PlayerInteractEvent interactEvent, InventoryClickEvent inventoryClickEvent) {
        if (!confirmPlayer.contains(player)) {
            player.getPlayer().sendMessage(ChatColor.RED + "Es-tu sûr de ce que tu viens de faire l'ami ? Clique à nouveau pour retourner au hub !");
            confirmPlayer.add(player);
            return;
        }
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("Hub");
        player.getPlayer().sendPluginMessage(Epikube.getInstance(), "BungeeCord", out.toByteArray());
        confirmPlayer.remove(player);
    }


}
