package com.protein.proteincore.queues;

import com.protein.proteincore.ProteinCore;
import com.protein.proteincore.callbacks.ChunkLoadCallBack;
import com.protein.proteincore.processes.GenerationProcess;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GenBucketTestQueue extends BukkitRunnable {

    private ConcurrentLinkedQueue<GenerationProcess> generationProcesses;
    private ProteinCore instance;

    public GenBucketTestQueue(ConcurrentLinkedQueue<GenerationProcess> generationProcesses) {
        this.generationProcesses = generationProcesses;
        this.instance = ProteinCore.getInstance();
    }

    @Override
    public void run() {
        ChunkLoadCallBack chunkLoadCallBack = generationProcess -> instance.getServer().getScheduler().runTaskAsynchronously(instance, () -> instance.getGenBucketManager().getGenerationProcesses().add(generationProcess));
        for (int i = 0; i < 400; i++) {
            GenerationProcess generationProcess = generationProcesses.poll();
            if ( generationProcess == null ) {
                cancel();
                return;
            }
            instance.getServer().getScheduler().runTask(instance, () -> {
                if ( !generationProcess.getPlacedLocation().getChunk().isLoaded() )
                    generationProcess.getPlacedLocation().getChunk().load();
                chunkLoadCallBack.call(generationProcess);
            });
        }
    }
}
