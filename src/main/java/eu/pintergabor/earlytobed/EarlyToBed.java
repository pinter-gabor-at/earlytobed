package eu.pintergabor.earlytobed;

import net.fabricmc.api.ModInitializer;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;

public class EarlyToBed implements ModInitializer {

    public static final String MODID = "earlytobed";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    // A wooden bucket
    public static WoodenBucketItem WOODEN_BUCKET_ITEM;
    // A wooden bucket, filled with water
    public static WoodenBucketItem WOODEN_WATER_BUCKET_ITEM;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("EarlyToBed started.");

        // Create and register wooden buckets
        WOODEN_BUCKET_ITEM = new WoodenBucketItem(Fluids.EMPTY, new FabricItemSettings().group(ItemGroup.MISC));
        WOODEN_WATER_BUCKET_ITEM = new WoodenBucketItem(Fluids.WATER, new FabricItemSettings().group(ItemGroup.MISC));
        Registry.register(Registry.ITEM,
                new Identifier(MODID, "wooden_bucket"), WOODEN_BUCKET_ITEM);
        Registry.register(Registry.ITEM,
                new Identifier(MODID, "wooden_water_bucket"), WOODEN_WATER_BUCKET_ITEM);
    }
}
