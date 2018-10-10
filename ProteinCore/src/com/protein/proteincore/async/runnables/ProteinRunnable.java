package com.protein.proteincore.async.runnables;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.async.callbacks.ProteinCallback;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class ProteinRunnable extends BukkitRunnable {

    public ProteinCallback callBack;

    public ProteinRunnable setCallBack(ProteinCallback callBack) {
        this.callBack = callBack;
        return this;
    }

    public void call(Object... objects) {
        Bukkit.getScheduler().runTask(ProteinCore.getInstance(), () -> callBack.call(objects));
    }

}
