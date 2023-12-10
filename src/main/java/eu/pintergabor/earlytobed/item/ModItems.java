package eu.pintergabor.earlytobed.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemGroups;

import static eu.pintergabor.earlytobed.util.Register.registerItem;

public final class ModItems {
    private ModItems() {
        // Static class
    }

    // A wooden bucket
    public static final WoodenBucketItem WOODEN_BUCKET_ITEM =
            new WoodenBucketItem(Fluids.EMPTY, new FabricItemSettings());
    // A wooden bucket, filled with water
    public static final WoodenBucketItem WOODEN_WATER_BUCKET_ITEM =
            new WoodenBucketItem(Fluids.WATER, new FabricItemSettings());
    // A wooden shears
    public static final WoodenShearsItem WOODEN_SHEARS_ITEM =
            new WoodenShearsItem(new FabricItemSettings().maxDamage(3));

    public static void Register() {
        // Create and register wooden buckets
        registerItem(
                "wooden_bucket", WOODEN_BUCKET_ITEM);
        registerItem(
                "wooden_water_bucket", WOODEN_WATER_BUCKET_ITEM);
        // Create and register wooden shears
        registerItem(
                "wooden_shears", WOODEN_SHEARS_ITEM);
        // Item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(
                entries -> {
                    entries.add(WOODEN_BUCKET_ITEM);
                    entries.add(WOODEN_WATER_BUCKET_ITEM);
                    entries.add(WOODEN_SHEARS_ITEM);
                });
    }
}
