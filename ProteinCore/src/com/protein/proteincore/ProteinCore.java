package com.protein.proteincore;

import com.protein.proteincore.command.CmdGenBucket;
import com.protein.proteincore.command.CmdTrenchTool;
import com.protein.proteincore.command.Command;
import com.protein.proteincore.enums.GenBucketType;
import com.protein.proteincore.fastblockupdate.FastBlockUpdate;
import com.protein.proteincore.guis.GenBucketGUI;
import com.protein.proteincore.listeners.GenBucketListener;
import com.protein.proteincore.listeners.ToolListener;
import com.protein.proteincore.managers.GenBucketManager;
import com.protein.proteincore.objs.GenBucket;
import com.protein.proteincore.objs.TrenchTool;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ProteinCore extends JavaPlugin {

    private static ProteinCore instance;
    /**
     * API constants
     */

    private Economy economy;
    /**
     * NMS classes
     */

    private FastBlockUpdate fastBlockUpdate;
    /**
     * Managers
     */

    /**
     * Lists
     */

    private ArrayList<TrenchTool> trenchTools = new ArrayList<>();


    private GenBucketManager genBucketManager;
    private Command[] commands;

    public static ProteinCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        commands = new Command[]{ new CmdGenBucket(), new CmdTrenchTool() };
        this.genBucketManager = new GenBucketManager(this);
        this.registerListners(new GenBucketListener(this), new GenBucketGUI(), new ToolListener());
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
        ConfigurationSection genbucketSection = getConfig().getConfigurationSection("genbuckets");
        genbucketSection.getKeys(false).forEach(typeKey -> {
            ConfigurationSection typeSection = genbucketSection.getConfigurationSection(typeKey);
            typeSection.getKeys(false).stream().map(genBucketKey -> new GenBucket(
                    GenBucketType.valueOf(typeKey.toUpperCase().replace("-", "_")),
                    typeSection.getDouble(genBucketKey + ".guiprice"),
                    typeSection.getDouble(genBucketKey + ".placement-price"),
                    genBucketManager.getItemStack("genbuckets." + typeKey + "." + genBucketKey + ".gui-item", genBucketKey),
                    genBucketManager.getItemStack("genbuckets." + typeKey + "." + genBucketKey + ".placement-item", genBucketKey),
                    Material.matchMaterial(genBucketKey.toUpperCase()))).forEach(genBucket -> genBucketManager.getGenBuckets().add(genBucket));
        });
        ConfigurationSection trenchToolSection = getConfig().getConfigurationSection("trenchtools");
        trenchToolSection.getKeys(false).forEach(key -> trenchTools.add(new TrenchTool(key, getItemStack(trenchToolSection.getString(key + ".material"), trenchToolSection.getString(key + ".name"), trenchToolSection.getStringList(key + ".lore")), Integer.parseInt(key.substring(0, 1)) / 2)));
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

    public ArrayList<TrenchTool> getTrenchTools() {
        return trenchTools;
    }

    private ItemStack getItemStack(String material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(material.toUpperCase()));
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> l = lore.stream().map(s1 -> ChatColor.translateAlternateColorCodes('&', s1)).collect(Collectors.toList());
        meta.setLore(l);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
