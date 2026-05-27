package me.pajic.depillage;

import net.minecraft.resources.Identifier;

@SuppressWarnings("LoggingSimilarMessage")
public class Depillage {

	public static final String MOD_ID = /*$ mod_id*/ "depillage";

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
