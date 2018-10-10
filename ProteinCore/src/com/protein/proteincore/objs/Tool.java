package com.protein.proteincore.objs;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Tool {

    private ItemStack itemStack;

    Tool(ItemStack itemStack) {
        this.itemStack = itemStack;
    }


    public ItemStack getItemStack() {
        return itemStack;
    }

    public abstract void run(Location location, Player player);
}
