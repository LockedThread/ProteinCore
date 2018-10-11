package com.protein.proteincore.modules;

import com.protein.proteincore.ProteinCore;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class AntiBowBoost extends Module {

    public AntiBowBoost(ProteinCore instance) {
        super(instance, "anti-bowboost", true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if ( event.getDamager() == null || event.getEntity() == null ) return;
        if ( event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE ) {
            if ( event.getEntity() instanceof Player && event.getDamager() instanceof Arrow ) {
                if ( ((Arrow) event.getDamager()).getShooter().equals(event.getEntity()) ) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
