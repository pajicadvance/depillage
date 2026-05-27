package me.pajic.depillage;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class DepillageTags {

	public static final TagKey<Structure> CLEARABLE = TagKey.create(
			Registries.STRUCTURE,
			Depillage.id("clearable")
	);
}
