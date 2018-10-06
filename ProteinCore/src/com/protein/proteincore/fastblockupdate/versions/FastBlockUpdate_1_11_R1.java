package com.protein.proteincore.fastblockupdate.versions;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.fastblockupdate.FastBlockUpdate;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.IBlockData;
import net.minecraft.server.v1_11_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;

public class FastBlockUpdate_1_11_R1 implements FastBlockUpdate {

    @Override
    public void run(Location location, Material material) {
        World w = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition bp = new BlockPosition(location.getX(), location.getY(), location.getZ());
        IBlockData ibd = net.minecraft.server.v1_11_R1.Block.getByCombinedId(material.getId());
        Bukkit.getScheduler().runTask(ProteinCore.getInstance(), () -> w.setTypeAndData(bp, ibd, 2));
    }
}
