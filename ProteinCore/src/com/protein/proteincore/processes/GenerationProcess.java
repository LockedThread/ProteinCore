package com.protein.proteincore.processes;

import org.bukkit.Location;
import org.bukkit.Material;

public abstract class GenerationProcess {

    public Material material;
    public Location placedLocation;

    public GenerationProcess(Material material, Location placedLocation) {
        this.material = material;
        this.placedLocation = placedLocation;
    }

    public abstract boolean execute();

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Location getPlacedLocation() {
        return placedLocation;
    }

    public void setPlacedLocation(Location placedLocation) {
        this.placedLocation = placedLocation;
    }
}
