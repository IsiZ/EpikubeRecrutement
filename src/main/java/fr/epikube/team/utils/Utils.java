package fr.epikube.team.utils;

import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static ItemStack saveStringToItemStack(ItemStack item, String key, String value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(item);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new net.minecraft.server.v1_8_R3.NBTTagCompound();
        itemCompound.set(key, new NBTTagString(value));
        nmsItem.setTag(itemCompound);
        return org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static String getStringFromItemStack(ItemStack item, String key, String def) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(item);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new net.minecraft.server.v1_8_R3.NBTTagCompound();
        if (itemCompound.hasKey(key)) {
            return itemCompound.getString(key);
        } else {
            return def;
        }
    }

    public static ItemStack saveIntToItemStack(ItemStack item, String key, int value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(item);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new net.minecraft.server.v1_8_R3.NBTTagCompound();
        itemCompound.set(key, new NBTTagInt(value));
        nmsItem.setTag(itemCompound);
        return org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static int getIntFromItemStack(ItemStack item, String key, int def) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(item);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new net.minecraft.server.v1_8_R3.NBTTagCompound();
        if (itemCompound.hasKey(key)) {
            return itemCompound.getInt(key);
        } else {
            return def;
        }
    }


}
