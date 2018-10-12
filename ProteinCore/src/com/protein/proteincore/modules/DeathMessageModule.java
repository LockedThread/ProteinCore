package com.protein.proteincore.modules;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.enums.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessageModule extends Module {

    public DeathMessageModule(ProteinCore instance) {
        super(instance, "death-message", true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(player.getKiller() != null ? Messages.DEATH_MESSAGE_KILLER.toString().replace("{player}", player.getName()).replace("{killer}", player.getKiller().getName()) : Messages.DEATH_MESSAGE_OTHER.toString().replace("{player}", event.getEntity().getName()).replace("{reason}", player.getLastDamageCause().getCause().name().toLowerCase()));
    }
}
