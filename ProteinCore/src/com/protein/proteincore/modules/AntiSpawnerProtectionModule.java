package com.protein.proteincore.modules;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.async.runnables.ProteinRunnable;
import com.protein.proteincore.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class AntiSpawnerProtectionModule extends Module {

    public AntiSpawnerProtectionModule(ProteinCore instance) {
        super(instance, "antiwater-protection", false);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if ( Utils.canEdit(event.getPlayer(), event.getBlockPlaced().getLocation()) ) {
            final Location location = event.getBlockPlaced().getLocation();
            if ( event.getBlockPlaced().getType() == Material.MOB_SPAWNER ) {
                new ProteinRunnable() {
                    @Override
                    public void run() {
                        int radius = 2;
                        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
                            for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                                for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                                    Block block = location.getWorld().getBlockAt(x, y, z);
                                    if ( block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER ) {
                                        ProteinCore.getInstance().getFastBlockUpdate().run(block.getLocation(), Material.AIR);
                                    }
                                }
                            }
                        }
                    }
                }.runTaskAsynchronously(ProteinCore.getInstance());
            }
        }
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        for (BlockFace blockFace : BlockFace.values()) {
            if ( event.getToBlock().getRelative(blockFace).getType() == Material.MOB_SPAWNER || event.getToBlock().getRelative(blockFace).getRelative(blockFace).getType() == Material.MOB_SPAWNER ) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void on(PlayerBucketEmptyEvent event) {
        if ( Utils.canEdit(event.getPlayer(), event.getBlockClicked().getRelative(event.getBlockFace()).getLocation()) ) {
            final Location location = event.getBlockClicked().getRelative(event.getBlockFace()).getLocation();
            if ( event.getItemStack() != null && event.getBucket() == Material.WATER_BUCKET ) {
                int radius = 2;
                for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
                    for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                        for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                            Block block = location.getWorld().getBlockAt(x, y, z);
                            if ( block.getType() == Material.MOB_SPAWNER ) {
                                event.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
