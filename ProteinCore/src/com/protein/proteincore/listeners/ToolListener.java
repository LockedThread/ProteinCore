package com.protein.proteincore.listeners;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.async.runnables.ProteinRunnable;
import com.protein.proteincore.enums.Messages;
import com.protein.proteincore.objs.TrenchTool;
import com.protein.proteincore.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ToolListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if ( event.isCancelled() || event.getPlayer().getItemInHand() == null || !event.getPlayer().getItemInHand().hasItemMeta() )
            return;
        final Player player = event.getPlayer();
        final ItemStack hand = player.getItemInHand();
        if ( !Utils.canEdit(player, event.getBlock().getLocation()) ) {
            player.sendMessage(Messages.CANT_EDIT.toString());
            event.setCancelled(true);
            return;
        }

        new ProteinRunnable() {
            @Override
            public void run() {
                for (TrenchTool trenchTool : ProteinCore.getInstance().getTrenchTools()) {
                    if ( Utils.isItem(trenchTool.getItemStack(), hand) ) {
                        trenchTool.run(event.getBlock().getLocation(), player);
                        return;
                    }
                }
            }
        }.runTaskAsynchronously(ProteinCore.getInstance());
    }
}