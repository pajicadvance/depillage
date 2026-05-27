package me.pajic.depillage.mixin;

import me.pajic.depillage.saveddata.StructureKillCounts;
import me.pajic.depillage.extension.ServerLevelExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin implements ServerLevelExtension {

    @Shadow public abstract SavedDataStorage getDataStorage();

    @Unique private StructureKillCounts depillage$structureKillCounts;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void initStructureKillCounts(CallbackInfo ci) {
        depillage$structureKillCounts = getDataStorage().computeIfAbsent(StructureKillCounts.getType());
    }

    @Override
    public StructureKillCounts depillage$getStructureKillCounts() {
        return depillage$structureKillCounts;
    }
}
