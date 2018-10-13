package com.protein.proteincore.listeners;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.enums.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if ( ProteinCore.getInstance().getFrozenUUIDs().contains(event.getPlayer().getUniqueId().toString()) ) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Messages.FREEZE_YOUVE_BEEN_FROZEN.toString());
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if ( ProteinCore.getInstance().getFrozenUUIDs().contains(event.getPlayer().getUniqueId().toString()) ) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Messages.FREEZE_YOUVE_BEEN_FROZEN.toString());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if ( ProteinCore.getInstance().getFrozenUUIDs().contains(event.getPlayer().getUniqueId().toString()) ) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Messages.FREEZE_YOUVE_BEEN_FROZEN.toString());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if ( event.getWhoClicked() instanceof Player && ProteinCore.getInstance().getFrozenUUIDs().contains(event.getWhoClicked().getUniqueId().toString()) ) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(Messages.FREEZE_YOUVE_BEEN_FROZEN.toString());
        }
    }
}
