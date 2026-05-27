# Depillage

Ever thought it's stupid that there is no way to prevent pillager outposts from spawning pillagers infinitely in a huge area? Yeah, me too.

This mod stops pillagers from spawning at their pillager outpost if enough pillagers have been killed near the outpost. Participating players will receive on-screen messages indicating their progress at the current outpost.

The mod is fully server-side but also works in singleplayer. If server-side, installing the mod on the client or installing [Polymer](https://modrinth.com/mod/polymer) on the server and enabling Auto-Host will enable custom text and translations for on-screen progress messages on the client, otherwise default English text will be used.

### Game rules
- `required_kills_to_prevent_spawning`: Amount of mob kills near the structure required to prevent spawning around the structure (default 100)
- `structure_kill_counting_radius`: Radius in blocks around structures where mob kills are counted (default 64)

### Configuring additional structures
The mod can actually be used to make any structure with unique mob spawns "clearable" - it's just preconfigured to work with pillager outposts out of the box. Simply add structures to the [`depillage:clearable` structure tag](https://github.com/pajicadvance/depillage/blob/multicutter-v2/src/main/resources/data/depillage/tags/worldgen/structure/clearable.json) using a data pack, and optionally provide [translations for on-screen progress messages](https://github.com/pajicadvance/depillage/blob/multicutter-v2/src/main/resources/assets/depillage/lang/en_us.json) (the mod will use default translations if none are provided). The mod will automatically detect which mob spawns are unique to the structure, count kills for those mobs, and prevent those mobs from spawning in that structure when enough have been killed.
