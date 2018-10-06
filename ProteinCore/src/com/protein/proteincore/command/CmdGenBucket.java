package com.protein.proteincore.command;

import com.protein.proteincore.enums.GenBucketType;
import com.protein.proteincore.enums.Messages;
import com.protein.proteincore.guis.GenBucketGUI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CmdGenBucket extends Command {

    public CmdGenBucket() {
        super("genbucket");
    }

    @Override
    public void run() {
        if ( args.length == 0 && isPlayer() ) {
            new GenBucketGUI().open(getPlayer());
        } else if ( !isPlayer() && args.length == 0 ) {
            sendMessage("&e/genbucket give [player] [material] [type] {amount}");
            sendMessage("&e/genbucket list - Shows possible Genbucket combinations");
        }
        if ( sender.hasPermission("genbucket.admin") ) {
            if ( args.length == 1 ) {
                if ( args[0].equalsIgnoreCase("help") ) {
                    sendMessage("&e/genbucket give [player] [material] [type] {amount}");
                    sendMessage("&e/genbucket list - Shows possible Genbucket combinations");
                }
                if ( args[0].equalsIgnoreCase("list") ) {
                    instance.getGenBucketManager().getGenBuckets().stream().map(genBucket -> "&eType: " + StringUtils.capitalize(genBucket.getGenBucketType().name().toLowerCase()) + " Material:" + StringUtils.capitalize(genBucket.getGenerationMaterial().name().toLowerCase())).forEach(this::sendMessage);
                }
            }
            if ( args.length > 1 && args.length < 6 ) {
                if ( args[0].equalsIgnoreCase("give") ) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if ( target == null ) {
                        sendMessage(Messages.NOT_ONLINE.toString().replace("{player}", args[0]));
                        return;
                    }
                    GenBucketType genBucketType = GenBucketType.valueOf(args[3].toUpperCase());
                    Material material = Material.matchMaterial(args[2].toUpperCase());
                    int amount = args.length == 5 ? Integer.parseInt(args[4]) : 1;
                    int i = 0;
                    while (i < amount) {
                        target.getInventory().addItem(instance.getGenBucketManager().find(genBucketType, material).getGuiItemStack());
                        i++;
                    }
                }
            }
        }
    }
}
