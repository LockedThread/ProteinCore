package com.protein.proteincore.command;

import com.google.common.base.Joiner;
import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.async.runnables.ProteinRunnable;
import com.protein.proteincore.enums.GenBucketType;
import com.protein.proteincore.enums.Messages;
import com.protein.proteincore.objs.TrenchTool;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CmdTrenchTool extends Command {

    public CmdTrenchTool() {
        super("trenchtool");
    }

    @Override
    public void run() {
        if ( !sender.hasPermission("trenchtool.give") ) return;
        if ( args.length == 0 || (args.length == 1 && getArg(0).equalsIgnoreCase("help")) ) {
            sendMessage("&e&l(!) &e/trenchtool give [type] {amount}");
            sendMessage("&e&l(!) &e/trenchtool list");
        } else if ( args.length == 1 ) {
            if ( getArg(0).equalsIgnoreCase("list") ) {
                sendMessage("&eTools: &c" + Joiner.on(", ").skipNulls().join(ProteinCore.getInstance().getTrenchTools()));
            }
        } else if ( args.length == 3 || args.length == 4 ) {
            if ( getArg(0).equalsIgnoreCase("give") ) {
                Player target = getTarget(1);
                if ( target == null ) {
                    sendMessage(Messages.NOT_ONLINE.toString().replace("{player}", getArg(0)));
                    return;
                }
                if ( args.length == 4 && isInt(getArg(3)) ) {
                    sendMessage(Messages.MUST_BE_POSITVE.toString().replace("{num}", getArg(3)));
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

                new ProteinRunnable() {
                    @Override
                    public void run() {
                        for (TrenchTool trenchTool : ProteinCore.getInstance().getTrenchTools()) {
                            if ( trenchTool.getIdentifier().equalsIgnoreCase(getArg(2)) ) {
                                int amount = args.length == 5 ? Integer.parseInt(args[4]) : 1;
                                int i = 0;
                                while (i < amount) {
                                    target.getInventory().addItem(trenchTool.getItemStack());
                                    i++;
                                }
                            }
                        }
                    }
                }.runTaskAsynchronously(instance);
            }
        }
    }
}
