package com.protein.proteincore.listeners;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.enums.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if ( ProteinCore.getInstance().getFrozenUUIDs().contains(event.getPlayer().getUniqueId().toString()) ) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Messages.FREEZE_YOUVE_BEEN_FROZEN.toString());
        }
    }
}
