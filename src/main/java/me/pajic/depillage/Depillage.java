package me.pajic.depillage;

//? if >=26.1 {
import net.minecraft.resources.Identifier;
//?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}

@SuppressWarnings("LoggingSimilarMessage")
public class Depillage {

	public static final String MOD_ID = /*$ mod_id*/ "depillage";

	//? if >=26.1 {
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
	//?} else {
	/*public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
	*///?}
}
