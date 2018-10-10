package com.protein.proteincore.command;

import com.google.common.base.Joiner;
import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.async.runnables.ProteinRunnable;
import com.protein.proteincore.enums.Messages;
import com.protein.proteincore.objs.TrenchTool;
import org.bukkit.entity.Player;

import java.util.stream.IntStream;

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

                new ProteinRunnable() {
                    @Override
                    public void run() {
                        for (TrenchTool trenchTool : ProteinCore.getInstance().getTrenchTools()) {
                            if ( trenchTool.getIdentifier().equalsIgnoreCase(getArg(2)) ) {
                                if ( args.length == 4 ) {
                                    IntStream.range(0, getArgAsInt(3)).forEach(i -> target.getInventory().addItem(trenchTool.getItemStack()));
                                    return;
                                }
                                target.getInventory().addItem(trenchTool.getItemStack());
                            }
                        }
                    }
                }.runTaskAsynchronously(instance);
            }
        }
    }
}
