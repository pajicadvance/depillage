package me.pajic.depillage.saveddata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.pajic.depillage.Depillage;
import me.pajic.depillage.DepillageGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureKillCounts extends SavedData {

	private final Map<BlockPos, StructureData> structures = new HashMap<>();

	private static final Codec<BlockPos> BLOCKPOS_STRING_CODEC = Codec.STRING.comapFlatMap(s -> DataResult.success(blockPosFromString(s), Lifecycle.stable()), BlockPos::toShortString);

    public static final Codec<StructureKillCounts> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.unboundedMap(BLOCKPOS_STRING_CODEC, StructureData.CODEC).fieldOf("structures").forGetter(o -> o.structures)
			).apply(instance, StructureKillCounts::new)
	);

	private static BlockPos blockPosFromString(String s) {
		String[] split = s.split(", ");
		return new BlockPos(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
	}

    public static final SavedDataType<StructureKillCounts> TYPE = new SavedDataType<>(
            Depillage.id("structure_kill_counts"),
			StructureKillCounts::new,
            CODEC,
            DataFixTypes.LEVEL
    );

    public static SavedDataType<StructureKillCounts> getType() {
        return TYPE;
    }

    public StructureKillCounts() {
        setDirty();
    }

    private StructureKillCounts(Map<BlockPos, StructureData> structures) {
        this.structures.putAll(structures);
        setDirty();
    }

	public void incrementStructureKills(BlockPos pos) {
		if (structures.containsKey(pos)) structures.get(pos).increment();
		setDirty();
	}

	public void initStructureData(String descriptionId, BlockPos pos, StructureSpawnOverride override) {
		if (!structures.containsKey(pos)) {
			List<EntityType<?>> spawns = new ArrayList<>();
			override.spawns().unwrap().forEach(spawnerDataWeighted -> spawns.add(spawnerDataWeighted.value().type()));
			structures.put(pos, new StructureData(descriptionId, spawns));
			setDirty();
		}
	}

    public boolean isStructureCleared(ServerLevel level, BlockPos pos) {
        return structures.containsKey(pos) && structures.get(pos).count >= level.getGameRules().get(DepillageGameRules.REQUIRED_KILLS);
    }

	public int getRemainingKillsForStructure(ServerLevel level, BlockPos pos) {
		int required = level.getGameRules().get(DepillageGameRules.REQUIRED_KILLS);
		return structures.containsKey(pos) ? required - structures.get(pos).count : required;
	}

	public List<EntityType<?>> getCountedEntitiesForStructure(BlockPos pos) {
		return structures.containsKey(pos) ? structures.get(pos).countedEntities : List.of();
	}

	public String getDescriptionIdForStructure(BlockPos pos) {
		return structures.containsKey(pos) ? structures.get(pos).descriptionId : "";
	}

	private static class StructureData {

		private final String descriptionId;
		private final List<EntityType<?>> countedEntities;
		private int count;

		public static final Codec<StructureData> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						Codec.STRING.fieldOf("descriptionId").forGetter(o -> o.descriptionId),
						EntityType.CODEC.listOf().fieldOf("countedEntities").forGetter(o -> o.countedEntities),
						Codec.INT.fieldOf("count").forGetter(o -> o.count)
				).apply(instance, StructureData::new)
		);

		private StructureData(String descriptionId, List<EntityType<?>> countedEntities, int count) {
			this.descriptionId = descriptionId;
			this.countedEntities = countedEntities;
			this.count = count;
		}

		public StructureData(String descriptionId, List<EntityType<?>> countedEntities) {
			this.descriptionId = descriptionId;
			this.countedEntities = countedEntities;
			this.count = 0;
		}

		public void increment() {
			count++;
		}
	}
}
