package me.pajic.depillage;

//? if >=26.1 {
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

public class DepillageGameRules {

	public static GameRule<Integer> REQUIRED_KILLS = positiveNonZeroIntRangeRule(100, Integer.MAX_VALUE);
	public static GameRule<Integer> KILL_COUNT_RADIUS = positiveNonZeroIntRangeRule(64, 96);

	public static int getInt(ServerLevel level, GameRule<Integer> rule) {
		return level.getGameRules().get(rule);
	}

	private static GameRule<Integer> positiveNonZeroIntRangeRule(int defaultValue, int max) {
		return new GameRule<>(
				GameRuleCategory.SPAWNING,
				GameRuleType.INT,
				IntegerArgumentType.integer(1, max),
				GameRuleTypeVisitor::visitInteger,
				Codec.intRange(1, max),
				i -> i,
				defaultValue,
				FeatureFlagSet.of()
		);
	}
}
//?} else {
/*import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;

public class DepillageGameRules {

	public static GameRules.Key<GameRules.IntegerValue> REQUIRED_KILLS = GameRules.register(
			"depillage.required_kills_to_prevent_spawning", GameRules.Category.SPAWNING, GameRules.IntegerValue.create(100)
	);
	public static GameRules.Key<GameRules.IntegerValue> KILL_COUNT_RADIUS = GameRules.register(
			"depillage.structure_kill_counting_radius", GameRules.Category.SPAWNING, GameRules.IntegerValue.create(64)
	);

	public static int getInt(ServerLevel level, GameRules.Key<GameRules.IntegerValue> rule) {
		return level.getGameRules().getInt(rule);
	}

	public static void init() {
		// Forces static initialization, registering the game rules.
	}
}
*///?}
