package com.protein.proteincore.guis;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.enums.GenBucketType;
import com.protein.proteincore.enums.Messages;
import com.protein.proteincore.managers.GenBucketManager;
import com.protein.proteincore.objs.GenBucket;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GenBucketGUI extends GUI {

    public GenBucketGUI() {
        super("&cGenBucket Shop", 27);
    }

    @Override
    public void open(Player player) {
        fill(Material.STAINED_GLASS_PANE, DyeColor.BLACK);
        GenBucketManager manager = ProteinCore.getInstance().getGenBucketManager();
        inventory.setItem(2, manager.find(GenBucketType.VERTICAL, Material.COBBLESTONE).getGuiItemStack());
        inventory.setItem(11, manager.find(GenBucketType.VERTICAL, Material.OBSIDIAN).getGuiItemStack());
        inventory.setItem(20, manager.find(GenBucketType.VERTICAL, Material.STONE).getGuiItemStack());

        inventory.setItem(4, manager.find(GenBucketType.VERTICAL, Material.GRAVEL).getGuiItemStack());
        inventory.setItem(13, manager.find(GenBucketType.VERTICAL, Material.SAND).getGuiItemStack());

        inventory.setItem(6, manager.find(GenBucketType.HORIZONTAL, Material.STONE).getGuiItemStack());
        inventory.setItem(15, manager.find(GenBucketType.HORIZONTAL, Material.COBBLESTONE).getGuiItemStack());
        inventory.setItem(24, manager.find(GenBucketType.HORIZONTAL, Material.OBSIDIAN).getGuiItemStack());
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if ( !(event.getWhoClicked() instanceof Player) || event.getClickedInventory() == null || event.getCurrentItem() == null || event.getClickedInventory().getSize() != 27 || !event.getClickedInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cGenBucket Shop")) ) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        GenBucket genBucket = ProteinCore.getInstance().getGenBucketManager().find(event.getCurrentItem());
        if ( genBucket == null ) return;
        double balance = ProteinCore.getInstance().getEconomy().getBalance(player);
        double guiPrice = genBucket.getGuiPrice();
        if ( balance >= guiPrice ) {
            player.getInventory().addItem(genBucket.getPlacementItemStack());
            player.sendMessage(Messages.GENBUCKET_PURCHASED.toString().replace("{genbucket}", StringUtils.capitalize(genBucket.getGenBucketType().name().toLowerCase()) + " " + StringUtils.capitalize(genBucket.getGenerationMaterial().name().toLowerCase())));
        } else {
            player.sendMessage(Messages.GENBUCKET_NO_MONEY.toString().replace("{action}", "purchase"));
        }
    }
}
