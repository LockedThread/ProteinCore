package com.protein.proteincore.modules;

import com.protein.proteincore.ProteinCore;
import org.bukkit.event.Listener;

public class Module implements Listener {

    private ProteinCore instance;
    private String name;
    private boolean defaultValue;

    public Module(ProteinCore instance, String name, boolean defaultValue) {
        this.instance = instance;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public ProteinCore getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }
}
