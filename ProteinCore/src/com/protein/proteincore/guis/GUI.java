package com.protein.proteincore.guis;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public abstract class GUI implements Listener {

    Inventory inventory;
    private String name;
    private int slots;

    GUI(String name, int slots) {
        this.name = StringUtils.center("     " + name, 32, "");
        this.slots = slots;
        this.inventory = Bukkit.createInventory(null, slots, ChatColor.translateAlternateColorCodes('&', name));
    }

    public abstract void open(Player player);

    public void open(Collection<Player> players) {
        players.forEach(this::open);
    }

    void fill(Material material, DyeColor dyeColor) {
        ItemStack panel = new ItemStack(material, 1, dyeColor.getData());
        panel.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta meta = panel.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        panel.setItemMeta(meta);

        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack itemStack = contents[i];
            if ( itemStack == null ) {
                inventory.setItem(i, panel);
            }
        }
    }
}
