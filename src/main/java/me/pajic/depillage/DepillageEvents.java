package me.pajic.depillage;

import me.pajic.depillage.extension.ServerLevelExtension;
import me.pajic.depillage.saveddata.StructureKillCounts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
//? if >=26.1 {
import net.minecraft.resources.Identifier;
//?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureStart;

import java.util.List;
import java.util.Optional;

public class DepillageEvents {

	public static void onPlayerKillStructureEnemy(ServerLevel level, Entity killer, LivingEntity victim) {
		if (killer instanceof Player) {
			BlockPos victimPos = victim.blockPosition();
			BlockPos structurePos = level.findNearestMapStructure(DepillageTags.CLEARABLE, victimPos, 0, false);
			if (structurePos != null) {
				int radius = DepillageGameRules.getInt(level, DepillageGameRules.KILL_COUNT_RADIUS);
				if (
						level.structureManager().getStructureWithPieceAt(victimPos, DepillageTags.CLEARABLE) != StructureStart.INVALID_START ||
						victimPos.distToCenterSqr(structurePos.getX(), victimPos.getY(), structurePos.getZ()) < radius * radius
				) {
					StructureKillCounts data = ((ServerLevelExtension) level).depillage$getStructureKillCounts();
					List<EntityType<?>> spawns = data.getCountedEntitiesForStructure(structurePos);
					if (spawns.stream().anyMatch(type -> victim.getType() == type)) {
						int remainingKills = data.getRemainingKillsForStructure(level, structurePos);
						if (remainingKills > 0) {
							data.incrementStructureKills(structurePos);
							String structureString = data.getDescriptionIdForStructure(structurePos);
							String entityTypeString = "";
							if (spawns.size() == 1 || spawns.stream().distinct().count() == 1) {
								//? if >=26.1 {
								Identifier entityTypeId = level.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE).getKey(spawns.getFirst());
								//?} else {
								/*ResourceLocation entityTypeId = level.registryAccess().registryOrThrow(Registries.ENTITY_TYPE).getKey(spawns.getFirst());
								*///?}
								if (entityTypeId != null) entityTypeString = "." + entityTypeId.getPath();
							}
							for (ServerPlayer player : level.getPlayers(serverPlayer -> {
								BlockPos playerPos = serverPlayer.blockPosition();
								return killer == serverPlayer ||
										playerPos.distToCenterSqr(structurePos.getX(), playerPos.getY(), structurePos.getZ()) < radius * radius ||
										playerPos.distToCenterSqr(killer.position()) < radius * radius;
							})) {
								if (remainingKills == 2) sendOverlay(player, Component.translatableWithFallback(
										"message.depillage" + entityTypeString + ".remainingEnemy", "1 enemy remaining"
								));
								else if (remainingKills > 1) sendOverlay(player, Component.translatableWithFallback(
										"message.depillage" + entityTypeString + ".remainingEnemies", remainingKills - 1 + " enemies remaining", remainingKills - 1
								));
								else sendOverlay(player, Component.translatableWithFallback(
										"message.depillage" + structureString + ".structureCleared", "Structure cleared!"
								));
							}
						}
					}
				}
			}
		}
	}

	public static boolean interceptSpawnAttempt(ServerLevel level, BlockPos pos, Structure structure, StructureSpawnOverride override, boolean original) {
		//? if >=26.1 {
		Registry<Structure> lookup = level.registryAccess().lookupOrThrow(Registries.STRUCTURE);
		Optional<HolderSet.Named<Structure>> opt = lookup.get(DepillageTags.CLEARABLE);
		Identifier id = lookup.getKey(structure);
		//?} else {
		/*Registry<Structure> lookup = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
		Optional<HolderSet.Named<Structure>> opt = lookup.getTag(DepillageTags.CLEARABLE);
		ResourceLocation id = lookup.getKey(structure);
		*///?}
		if (opt.isPresent() && id != null && opt.get().stream().anyMatch(holder -> holder.is(id))) {
			BlockPos structurePos = level.findNearestMapStructure(DepillageTags.CLEARABLE, pos, 0, false);
			if (structurePos != null) {
				StructureKillCounts data = ((ServerLevelExtension) level).depillage$getStructureKillCounts();
				data.initStructureData("." + id.getPath(), structurePos, override);
				return !data.isStructureCleared(level, structurePos);
			}
		}
		return original;
	}

	private static void sendOverlay(ServerPlayer player, Component message) {
		//? if >=26.1 {
		player.sendOverlayMessage(message);
		//?} else {
		/*player.displayClientMessage(message, true);
		*///?}
	}
}
