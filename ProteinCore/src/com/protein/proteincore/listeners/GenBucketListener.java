package com.protein.proteincore.listeners;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.enums.Messages;
import com.protein.proteincore.objs.GenBucket;
import com.protein.proteincore.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class GenBucketListener implements Listener {

    private ProteinCore instance;

    public GenBucketListener(ProteinCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if ( event.isCancelled() || event.getBlockPlaced() == null || event.getItemInHand() == null || !event.getItemInHand().hasItemMeta() )
            return;
        if ( Utils.canEdit(event.getPlayer(), event.getBlockPlaced().getLocation()) ) {
            GenBucket genBucket = instance.getGenBucketManager().find(event.getItemInHand());
            if ( genBucket == null ) return;
            double balance = instance.getEconomy().getBalance(player);
            double placementCost = genBucket.getPlacementPrice();
            if ( balance >= placementCost ) {
                instance.getGenBucketManager().getGenerationProcesses().add(genBucket.getProcess(event.getBlockPlaced().getLocation(), event.getBlockPlaced().getFace(event.getBlockAgainst()).getOppositeFace()));
                instance.getEconomy().withdrawPlayer(player, placementCost);
                player.sendMessage(Messages.GENBUCKET_PLACED.toString().replace("{money}", String.valueOf(placementCost)));
                player.setItemInHand(event.getItemInHand());
            } else {
                player.sendMessage(Messages.GENBUCKET_NO_MONEY.toString().replace("{action}", "placed"));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        instance.getServer().getScheduler().runTaskAsynchronously(instance, () -> instance.getGenBucketManager().getGenerationProcesses().stream().filter(generationProcess -> generationProcess.getPlacedLocation().equals(event.getBlock().getLocation())).forEach(generationProcess -> {
            instance.getGenBucketManager().getGenerationProcesses().remove(generationProcess);
            event.getPlayer().sendMessage(Messages.GENBUCKET_CANCELLED_PROCESS.toString().replace("{loc}", Utils.prettyLocation(generationProcess.getPlacedLocation())));
        }));
    }
}
