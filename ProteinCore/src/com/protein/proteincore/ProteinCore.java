package com.protein.proteincore;

import com.protein.proteincore.command.CmdGenBucket;
import com.protein.proteincore.command.Command;
import com.protein.proteincore.enums.GenBucketType;
import com.protein.proteincore.fastblockupdate.FastBlockUpdate;
import com.protein.proteincore.guis.GenBucketGUI;
import com.protein.proteincore.listeners.GenBucketListener;
import com.protein.proteincore.managers.GenBucketManager;
import com.protein.proteincore.objs.GenBucket;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;

public class ProteinCore extends JavaPlugin {

    private static ProteinCore instance;
    /**
     * API constants
     */

    public Economy economy;
    /**
     * NMS classes
     */

    public FastBlockUpdate fastBlockUpdate;
    /**
     * Managers
     */
    public GenBucketManager genBucketManager;
    private Command[] commands;

    public static ProteinCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        commands = new Command[]{ new CmdGenBucket() };
        this.genBucketManager = new GenBucketManager(this);
        this.registerListners(new GenBucketListener(this), new GenBucketGUI());
        this.setupConfig();
        this.setupFastRemove();
        this.setupVault();

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void setupConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        ConfigurationSection rootSection = getConfig().getConfigurationSection("genbuckets");
        rootSection.getKeys(false).forEach(typeKey -> {
            ConfigurationSection typeSection = rootSection.getConfigurationSection(typeKey);
            typeSection.getKeys(false).stream().map(genBucketKey -> new GenBucket(
                    GenBucketType.valueOf(typeKey.toUpperCase().replace("-", "_")),
                    typeSection.getDouble(genBucketKey + ".guiprice"),
                    typeSection.getDouble(genBucketKey + ".placement-price"),
                    genBucketManager.getItemStack("genbuckets." + typeKey + "." + genBucketKey + ".gui-item", genBucketKey),
                    genBucketManager.getItemStack("genbuckets." + typeKey + "." + genBucketKey + ".placement-item", genBucketKey),
                    Material.matchMaterial(genBucketKey.toUpperCase()))).forEach(genBucket -> genBucketManager.getGenBuckets().add(genBucket));
        });
    }

    private void setupFastRemove() {
        String version = getServer().getClass().getPackage().getName().replace("v", "").split("\\.")[3];

        try {
            fastBlockUpdate = (FastBlockUpdate) Class.forName("com.protein.proteincore.fastblockupdate.versions.FastBlockUpdate_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            getLogger().log(Level.SEVERE, "Invalid Version, must use one of these: {1.8.8, 1.9.4, 1.10.2, 1.11.2, 1.12.2}");
            getPluginLoader().disablePlugin(this);
        }
    }

    private void setupVault() {
        if ( getServer().getPluginManager().getPlugin("Vault") == null )
            return;

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if ( rsp == null ) {
            return;
        }
        economy = rsp.getProvider();
    }

    private void registerListners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] args) {
        Arrays.stream(commands).filter(command -> bukkitCommand.getName().equalsIgnoreCase(command.commandName)).forEach(command -> {
            command.init(sender, args);
            command.run();
        });
        return true;
    }

    /**
     * Getters
     */

    public GenBucketManager getGenBucketManager() {
        return genBucketManager;
    }

    public Economy getEconomy() {
        return economy;
    }

    public FastBlockUpdate getFastBlockUpdate() {
        return fastBlockUpdate;
    }
}
