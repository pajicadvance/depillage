package me.pajic.depillage.mixin;

import me.pajic.depillage.saveddata.StructureKillCounts;
import me.pajic.depillage.extension.ServerLevelExtension;
import net.minecraft.server.level.ServerLevel;
//? if >=26.1 {
import net.minecraft.world.level.storage.SavedDataStorage;
//?} else {
/*import net.minecraft.world.level.storage.DimensionDataStorage;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin implements ServerLevelExtension {

    //? if >=26.1 {
    @Shadow public abstract SavedDataStorage getDataStorage();
    //?} else {
    /*@Shadow public abstract DimensionDataStorage getDataStorage();
    *///?}

    @Unique private StructureKillCounts depillage$structureKillCounts;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void initStructureKillCounts(CallbackInfo ci) {
        //? if >=26.1 {
        depillage$structureKillCounts = getDataStorage().computeIfAbsent(StructureKillCounts.getType());
        //?} else {
        /*depillage$structureKillCounts = getDataStorage().computeIfAbsent(StructureKillCounts.FACTORY, "depillage_structure_kill_counts");
        *///?}
    }

    @Override
    public StructureKillCounts depillage$getStructureKillCounts() {
        return depillage$structureKillCounts;
    }
}
