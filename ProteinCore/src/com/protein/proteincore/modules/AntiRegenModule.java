package com.protein.proteincore.modules;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.async.runnables.ProteinRunnable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class AntiRegenModule extends Module {

    public AntiRegenModule(ProteinCore instance) {
        super(instance, "anti-regen", true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if ( event.getEntityType() == EntityType.PRIMED_TNT ) {
            final Location location = event.getLocation();
            new ProteinRunnable() {
                @Override
                public void run() {
                    int radius = 2;
                    for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
                        for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                                Block block = location.getWorld().getBlockAt(x, y, z);
                                if ( block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA ) {
                                    getInstance().getFastBlockUpdate().run(block.getLocation(), Material.AIR);
                                }
                            }
                        }
                    }
                }
            }.runTaskAsynchronously(getInstance());
        }
    }
}
