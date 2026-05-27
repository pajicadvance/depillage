package me.pajic.depillage.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.pajic.depillage.Depillage;
import me.pajic.depillage.DepillageEvents;
import me.pajic.depillage.DepillageGameRules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.GAME_RULE, Depillage.id("required_kills_to_prevent_spawning"), DepillageGameRules.REQUIRED_KILLS);
		Registry.register(BuiltInRegistries.GAME_RULE, Depillage.id("structure_kill_counting_radius"), DepillageGameRules.KILL_COUNT_RADIUS);
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((level, entity, killedEntity, _) ->
				DepillageEvents.onPlayerKillStructureEnemy(level, entity, killedEntity)
		);
	}
}
//?}
