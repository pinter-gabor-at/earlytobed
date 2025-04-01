package eu.pintergabor.earlytobed;

import eu.pintergabor.earlytobed.item.ModItems;

import net.fabricmc.api.ModInitializer;


public final class Mod implements ModInitializer {

	@Override
	public void onInitialize() {
		// Register items.
		ModItems.register();
	}
}
