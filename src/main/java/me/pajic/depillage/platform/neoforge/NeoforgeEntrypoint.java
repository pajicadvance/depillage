package me.pajic.depillage.platform.neoforge;

//? if neoforge && >=26.1 {

/*import me.pajic.depillage.Depillage;
import me.pajic.depillage.DepillageEvents;
import me.pajic.depillage.DepillageGameRules;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Depillage.MOD_ID)
@EventBusSubscriber(modid = Depillage.MOD_ID)
public class NeoforgeEntrypoint {

	@SuppressWarnings("resource")
	@SubscribeEvent
	private static void onPlayerKillEntity(LivingDeathEvent event) {
		if (event.getEntity().level() instanceof ServerLevel level) {
			DepillageEvents.onPlayerKillStructureEnemy(level, event.getSource().getEntity(), event.getEntity());
		}
	}

	@SubscribeEvent
	private static void registerGameRules(RegisterEvent event) {
		event.register(
				Registries.GAME_RULE,
				registry -> {
					registry.register(Depillage.id("required_kills_to_prevent_spawning"), DepillageGameRules.REQUIRED_KILLS);
					registry.register(Depillage.id("structure_kill_counting_radius"), DepillageGameRules.KILL_COUNT_RADIUS);
				}
		);
	}
}
*///?}

//? if neoforge && <26.1 {

/*import me.pajic.depillage.Depillage;
import me.pajic.depillage.DepillageEvents;
import me.pajic.depillage.DepillageGameRules;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@Mod(Depillage.MOD_ID)
@EventBusSubscriber(modid = Depillage.MOD_ID)
public class NeoforgeEntrypoint {

	public NeoforgeEntrypoint() {
		DepillageGameRules.init();
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onPlayerKillEntity(LivingDeathEvent event) {
		if (event.getEntity().level() instanceof ServerLevel level) {
			DepillageEvents.onPlayerKillStructureEnemy(level, event.getSource().getEntity(), event.getEntity());
		}
	}
}
*///?}
