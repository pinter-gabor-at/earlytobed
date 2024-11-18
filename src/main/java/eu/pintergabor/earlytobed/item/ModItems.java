package eu.pintergabor.earlytobed.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ShearsItem;

import static eu.pintergabor.earlytobed.util.Register.registerItem;

public final class ModItems {
    // A wooden bucket
    public static final WoodenBucketItem WOODEN_BUCKET_ITEM =
            new WoodenBucketItem(Fluids.EMPTY, new Item.Settings());
    // A wooden bucket, filled with water
    public static final WoodenBucketItem WOODEN_WATER_BUCKET_ITEM =
            new WoodenBucketItem(Fluids.WATER, new Item.Settings());
    // A wooden shears
    public static final ShearsItem WOODEN_SHEARS_ITEM =
            new ShearsItem(new Item.Settings().maxDamage(3));

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
