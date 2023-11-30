package eu.pintergabor.earlytobed;

import eu.pintergabor.earlytobed.item.ModItems;
import net.fabricmc.api.ModInitializer;

import static eu.pintergabor.earlytobed.Constants.*;

public final class EarlyToBed implements ModInitializer {

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("EarlyToBed started.");
        // Register items
        ModItems.Register();
    }
}
