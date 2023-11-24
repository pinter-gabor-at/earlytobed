package eu.pintergabor.earlytobed;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static eu.pintergabor.earlytobed.Global.*;

public final class Items {
    private Items() {
        // Static class
    }

    // A wooden bucket
    public static final WoodenBucketItem WOODEN_BUCKET_ITEM =
            new WoodenBucketItem(Fluids.EMPTY, new FabricItemSettings());
    // A wooden bucket, filled with water
    public static WoodenBucketItem WOODEN_WATER_BUCKET_ITEM =
            new WoodenBucketItem(Fluids.WATER, new FabricItemSettings());
    // A wooden shears
    public static final WoodenShearsItem WOODEN_SHEARS_ITEM =
            new WoodenShearsItem(new FabricItemSettings().maxDamage(3));

    public static void Register() {
        // Create and register wooden buckets
        Registry.register(Registries.ITEM,
                new Identifier(MODID, "wooden_bucket"), WOODEN_BUCKET_ITEM);
        Registry.register(Registries.ITEM,
                new Identifier(MODID, "wooden_water_bucket"), WOODEN_WATER_BUCKET_ITEM);
        Registry.register(Registries.ITEM,
                new Identifier(MODID, "wooden_shears"), WOODEN_SHEARS_ITEM);
    }

}
