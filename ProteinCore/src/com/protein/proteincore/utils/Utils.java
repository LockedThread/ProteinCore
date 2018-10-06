package com.protein.proteincore.utils;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static String prettyLocation(Location location) {
        return location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }

    public static boolean canEdit(Player player, Location location) {
        return FPlayers.getInstance().getByPlayer(player).getRelationTo(Board.getInstance().getFactionAt(new FLocation(location))) == Relation.MEMBER
                || Board.getInstance().getFactionAt(new FLocation(location)).isWilderness();
    }

    public static boolean isItem(ItemStack a, ItemStack b) {
        return a != null && b != null && a.getType() == b.getType() && ((a.hasItemMeta() && b.hasItemMeta()) && Bukkit.getItemFactory().equals(a.getItemMeta(), b.getItemMeta()) || !a.hasItemMeta() && !b.hasItemMeta());
    }
}
