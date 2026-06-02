package me.pajic.depillage;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

public class DepillageGameRules {

	public static GameRule<Integer> REQUIRED_KILLS = positiveNonZeroIntRangeRule(100, Integer.MAX_VALUE);
	public static GameRule<Integer> KILL_COUNT_RADIUS = positiveNonZeroIntRangeRule(64, 96);

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
