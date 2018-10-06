package com.protein.proteincore.tasks;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.processes.GenerationProcess;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GenerateTask extends BukkitRunnable {

    @Override
    public void run() {
        ChunkLoadCallBack chunkLoadCallBack = generationProcess -> Bukkit.getScheduler().runTaskAsynchronously(ProteinCore.getInstance(), () -> {
            if ( !generationProcess.execute() ) {
                ProteinCore.getInstance().getGenBucketManager().getGenerationProcesses().remove(generationProcess);
            }
        });
        ProteinCore.getInstance().getGenBucketManager().getGenerationProcesses().forEach(generationProcess -> Bukkit.getScheduler().runTask(ProteinCore.getInstance(), () -> {
            if ( !generationProcess.placedLocation.getChunk().isLoaded() ) {
                generationProcess.placedLocation.getChunk().load();
            }
            chunkLoadCallBack.call(generationProcess);
        }));
    }

    interface ChunkLoadCallBack {
        void call(GenerationProcess generationProcess);
    }
}
