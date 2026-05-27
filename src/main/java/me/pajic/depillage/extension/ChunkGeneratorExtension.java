package me.pajic.depillage.extension;

import net.minecraft.server.level.ServerLevel;

public interface ChunkGeneratorExtension {
    void depillage$setServerLevel(ServerLevel serverLevel);
}
