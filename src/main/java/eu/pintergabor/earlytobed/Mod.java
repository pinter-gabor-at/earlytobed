package eu.pintergabor.earlytobed;

import eu.pintergabor.earlytobed.item.ModItems;

import net.fabricmc.api.ModInitializer;

public final class Mod implements ModInitializer {

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// Register items
		ModItems.register();
	}
}
