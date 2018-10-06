package com.protein.proteincore.processes;

import com.protein.proteincore.ProteinCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class HorizontalProcess extends GenerationProcess {

    private int current;
    private BlockFace blockFace;

    public HorizontalProcess(Material material, Location placedLocation, BlockFace blockFace) {
        super(material, placedLocation);
        this.blockFace = blockFace;
        this.current = 1;
    }

    @Override
    public boolean execute() {
        if ( current >= ProteinCore.getInstance().getConfig().getInt("horizontal-distance") ) return false;

        Block currentBlock = placedLocation.getBlock().getRelative(blockFace, current);
        if ( currentBlock.getType() == Material.AIR ) {
            ProteinCore.getInstance().getFastBlockUpdate().run(currentBlock.getLocation(), material);
            current++;
            return true;
        }

        return false;
    }
}
