package com.protein.proteincore.objs;

import com.protein.proteincore.enums.GenBucketType;
import com.protein.proteincore.processes.GenerationProcess;
import com.protein.proteincore.processes.HorizontalProcess;
import com.protein.proteincore.processes.VerticalProcess;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class GenBucket {

    private GenBucketType genBucketType;
    private double guiPrice;
    private double placementPrice;
    private ItemStack guiItemStack;
    private ItemStack placementItemStack;
    private Material generationMaterial;

    public GenBucket(GenBucketType genBucketType, double guiPrice, double placementPrice, ItemStack guiItemStack, ItemStack placementItemStack, Material generationMaterial) {
        this.genBucketType = genBucketType;
        this.guiPrice = guiPrice;
        this.placementPrice = placementPrice;
        this.guiItemStack = guiItemStack;
        this.placementItemStack = placementItemStack;
        this.generationMaterial = generationMaterial;
    }

    public GenBucketType getGenBucketType() {
        return genBucketType;
    }

    public double getGuiPrice() {
        return guiPrice;
    }

    public double getPlacementPrice() {
        return placementPrice;
    }

    public ItemStack getGuiItemStack() {
        return guiItemStack;
    }

    public ItemStack getPlacementItemStack() {
        return placementItemStack;
    }

    public Material getGenerationMaterial() {
        return generationMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        GenBucket genBucket = (GenBucket) o;

        return Double.compare(genBucket.guiPrice, guiPrice) == 0 && Double.compare(genBucket.placementPrice, placementPrice) == 0 && genBucketType == genBucket.genBucketType && (guiItemStack != null ? guiItemStack.equals(genBucket.guiItemStack) : genBucket.guiItemStack == null) && (placementItemStack != null ? placementItemStack.equals(genBucket.placementItemStack) : genBucket.placementItemStack == null) && generationMaterial == genBucket.generationMaterial;
    }

    public GenerationProcess getProcess(Location location, BlockFace blockFace) {
        switch (genBucketType) {
            case HORIZONTAL:
                return new HorizontalProcess(getGenerationMaterial(), location, blockFace);
            case VERTICAL:
                return new VerticalProcess(getGenerationMaterial(), location);
        }
        return null;
    }
}
