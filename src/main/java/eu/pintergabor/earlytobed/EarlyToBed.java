package eu.pintergabor.earlytobed;

import net.fabricmc.api.ModInitializer;

import static eu.pintergabor.earlytobed.Global.*;

public final class EarlyToBed implements ModInitializer {

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("EarlyToBed started.");
        // Register items
        Items.Register();
    }
}
