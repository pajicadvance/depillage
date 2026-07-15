package me.pajic.depillage.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.depillage.extension.ChunkGeneratorExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @Inject(
            method = "mobsAt",
            at = @At("HEAD")
    )
    private static void passServerLevel(
            CallbackInfoReturnable<?> cir,
            //? if >=26.1 {
            @Local(argsOnly = true, name = "level") ServerLevel level,
            @Local(argsOnly = true, name = "generator") ChunkGenerator generator
            //?} else {
            /*@Local(argsOnly = true) ServerLevel level,
            @Local(argsOnly = true) ChunkGenerator generator
            *///?}
    ) {
        ((ChunkGeneratorExtension) generator).depillage$setServerLevel(level);
    }
}
