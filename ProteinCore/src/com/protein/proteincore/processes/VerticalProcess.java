package com.protein.proteincore.processes;

import com.protein.proteincore.ProteinCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class VerticalProcess extends GenerationProcess {

    private int current;

    public VerticalProcess(Material material, Location placedLocation) {
        super(material, placedLocation);
        this.current = placedLocation.getBlockY() + 1;
    }

    @Override
    public boolean execute() {
        if ( current > 254 || placedLocation.getBlock().getType() != material ) return false;

        Block currentBlock = placedLocation.getBlock().getRelative(BlockFace.UP, current - placedLocation.getBlockY());
        if ( currentBlock.getType() == Material.AIR ) {
            ProteinCore.getInstance().getFastBlockUpdate().run(currentBlock.getLocation(), material);
            current++;
            return true;
        }
        return false;
    }
}
