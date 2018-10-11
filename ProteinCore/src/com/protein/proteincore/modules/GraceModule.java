package com.protein.proteincore.modules;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.enums.Messages;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GraceModule extends Module {

    public GraceModule(ProteinCore instance) {
        super(instance, "grace", false);
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if ( event.getItem() == null ) return;
        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            Player player = event.getPlayer();
            if ( event.getClickedBlock().getType() == Material.TNT && event.getItem().getType() == Material.FLINT_AND_STEEL ) {
                event.setCancelled(true);
                player.sendMessage(Messages.GRACE_PERIOD_DENY.toString());
            } else if ( event.getItem().getType() == Material.FLINT_AND_STEEL || event.getItem().getType() == Material.FIREBALL ) {
                event.setCancelled(true);
                player.sendMessage(Messages.GRACE_PERIOD_DENY.toString());
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if ( event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG && event.getEntityType() == EntityType.CREEPER ) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        if ( event.getSource().getType() == Material.FIRE ) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if ( event.getBlockPlaced().getType() == Material.TNT ) {
            event.setCancelled(true);
        }
    }
}
