package com.protein.proteincore.listeners;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.async.runnables.ProteinRunnable;
import com.protein.proteincore.enums.Messages;
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
                ProteinCore.getInstance().getTrenchTools().stream().filter(trenchTool -> Utils.isItem(trenchTool.getItemStack(), hand)).findFirst().ifPresent(trenchTool -> trenchTool.run(event.getBlock().getLocation(), player));
            }
        }.runTaskAsynchronously(ProteinCore.getInstance());
    }
}