package me.pajic.depillage.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.depillage.DepillageEvents;
import me.pajic.depillage.extension.ChunkGeneratorExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin implements ChunkGeneratorExtension {

    @Unique private ServerLevel depillage$serverLevel;

    @ModifyExpressionValue(
            method = "getMobsAt",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/commons/lang3/mutable/MutableBoolean;isTrue()Z"
            )
    )
    private boolean noStructureMobSpawnsIfCleared(
			boolean original,
			//? if >=26.1 {
			@Local(argsOnly = true, name = "pos") BlockPos pos,
			@Local(name = "structure") Structure structure,
			@Local(name = "override") StructureSpawnOverride override
			//?} else {
			/*@Local(argsOnly = true) BlockPos pos,
			@Local Structure structure,
			@Local StructureSpawnOverride override
			*///?}
	) {
		return DepillageEvents.interceptSpawnAttempt(depillage$serverLevel, pos, structure, override, original);
    }

	@Override
	public void depillage$setServerLevel(ServerLevel serverLevel) {
		this.depillage$serverLevel = serverLevel;
	}
}
