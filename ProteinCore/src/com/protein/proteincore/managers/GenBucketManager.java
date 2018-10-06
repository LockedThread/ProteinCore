package com.protein.proteincore.managers;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.enums.GenBucketType;
import com.protein.proteincore.objs.GenBucket;
import com.protein.proteincore.processes.GenerationProcess;
import com.protein.proteincore.tasks.GenerateTask;
import com.protein.proteincore.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class GenBucketManager extends Manager {

    private ConcurrentLinkedQueue<GenerationProcess> generationProcesses;
    private ArrayList<GenBucket> genBuckets;

    public GenBucketManager(ProteinCore instance) {
        super(instance);
        generationProcesses = new ConcurrentLinkedQueue<>();
        genBuckets = new ArrayList<>();

        new GenerateTask().runTaskTimerAsynchronously(instance, 25L, 25L);
    }

    public ConcurrentLinkedQueue<GenerationProcess> getGenerationProcesses() {
        return generationProcesses;
    }

    public GenBucket find(GenBucketType genBucketType, Material material) {
        return genBuckets.stream().filter(genBucket -> genBucket.getGenBucketType() == genBucketType && genBucket.getGenerationMaterial() == material).findFirst().orElse(null);
    }

    public GenBucket find(ItemStack itemStack) {
        for (GenBucket genBucket : genBuckets) {
            if ( Utils.isItem(genBucket.getPlacementItemStack(), itemStack) || Utils.isItem(genBucket.getGuiItemStack(), itemStack) ) {
                return genBucket;
            }
        }
        return null;
    }


    public ItemStack getItemStack(String s, String material) {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(material.toUpperCase()));
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString(s + ".name")));
        List<String> lore = instance.getConfig().getStringList(s + ".lore").stream().map(s1 -> ChatColor.translateAlternateColorCodes('&', s1)).collect(Collectors.toList());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ArrayList<GenBucket> getGenBuckets() {
        return genBuckets;
    }

}
