package com.protein.proteincore.command;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.enums.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CmdFreeze extends Command {

    public CmdFreeze() {
        super("freeze");
    }

    @Override
    public void run() {
        if ( sender.hasPermission("freeze.admin") ) {
            if ( args.length == 0 ) {
                sendMessage("&e/freeze [Player]");
            } else if ( args.length == 1 ) {
                Player target = getTarget(0);
                if ( target == null ) {
                    sendMessage(Messages.NOT_ONLINE.toString().replace("{player}", getArg(0)));
                    return;
                }
                if ( target.isOp() || target.hasPermission("freeze.bypass") || (isPlayer() && target.getName().equals(getPlayer().getName())) ) {
                    sendMessage(Messages.FREEZE_ERROR.toString().replace("{player}", target.getName()));
                    return;
                }
                if ( instance.getFrozenUUIDs().contains(target.getUniqueId().toString()) ) {
                    instance.getFrozenUUIDs().remove(target.getUniqueId().toString());
                    sendMessage(Messages.FREEZE_UNFROZEN.toString().replace("{player}", target.getName()));
                    target.sendMessage(Messages.FREEZE_YOUVE_BEEN_UNFROZEN.toString());
                } else {
                    instance.getFrozenUUIDs().add(target.getUniqueId().toString());
                    sendMessage(Messages.FREEZE_FROZEN.toString().replace("{player}", target.getName()));
                    target.sendMessage(Messages.FREEZE_YOUVE_BEEN_FROZEN.toString());
                    if ( ProteinCore.getInstance().getConfig().getBoolean("broadcast-frozen") ) {
                        Bukkit.broadcastMessage(Messages.FREEZE_BROADCAST_FROZEN.toString().replace("{player}", target.getName()));
                    }
                }
            }
        }
    }
}
