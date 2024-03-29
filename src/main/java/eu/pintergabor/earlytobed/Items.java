package eu.pintergabor.earlytobed;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static eu.pintergabor.earlytobed.Global.*;

public final class Items {
    private Items() {
        // Static class
    }

    // A wooden bucket
    public static final WoodenBucketItem WOODEN_BUCKET_ITEM =
            new WoodenBucketItem(Fluids.EMPTY, new FabricItemSettings().group(ItemGroup.MISC));
    // A wooden bucket, filled with water
    public static WoodenBucketItem WOODEN_WATER_BUCKET_ITEM =
            new WoodenBucketItem(Fluids.WATER, new FabricItemSettings().group(ItemGroup.MISC));
    // A wooden shears
    public static final WoodenShearsItem WOODEN_SHEARS_ITEM =
            new WoodenShearsItem(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(3));

    public static void Register() {
        // Create and register wooden buckets
        Registry.register(Registry.ITEM,
                new Identifier(MODID, "wooden_bucket"), WOODEN_BUCKET_ITEM);
        Registry.register(Registry.ITEM,
                new Identifier(MODID, "wooden_water_bucket"), WOODEN_WATER_BUCKET_ITEM);
        Registry.register(Registry.ITEM,
                new Identifier(MODID, "wooden_shears"), WOODEN_SHEARS_ITEM);
    }

}
