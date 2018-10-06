package com.protein.proteincore.fastblockupdate.versions;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.fastblockupdate.FastBlockUpdate;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class FastBlockUpdate_1_8_R3 implements FastBlockUpdate {

    @Override
    public void run(Location location, Material material) {
        World w = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition bp = new BlockPosition(location.getX(), location.getY(), location.getZ());
        IBlockData ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(material.getId());
        Bukkit.getScheduler().runTask(ProteinCore.getInstance(), () -> w.setTypeAndData(bp, ibd, 2));
    }
}
