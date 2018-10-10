package com.protein.proteincore.objs;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TrenchTool extends Tool {

    private final String identifier;
    private final int radius;

    public TrenchTool(String identifier, ItemStack itemStack, int radius) {
        super(itemStack);
        this.identifier = identifier;
        this.radius = radius;
    }

    @Override
    public void run(Location location, Player player) {
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                if ( Utils.canEdit(player, new Location(location.getWorld(), x, location.getY(), z)) ) {
                    for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                        Location blockLocation = new Location(location.getWorld(), x, y, z);
                        if ( blockLocation.getBlock().getType() == Material.BEDROCK || blockLocation.getBlock().getType() == Material.MOB_SPAWNER || blockLocation.getBlock().getType() == Material.AIR )
                            continue;
                        ProteinCore.getInstance().getFastBlockUpdate().run(blockLocation, Material.AIR);
                    }
                }
            }
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
