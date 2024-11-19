package eu.pintergabor.earlytobed.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ShearsItem;

import static eu.pintergabor.earlytobed.util.Register.registerItem;

public final class ModItems {
    // A wooden bucket
    public static final WoodenBucketItem WOODEN_BUCKET_ITEM = (WoodenBucketItem) registerItem(
            "wooden_bucket",
            settings -> new WoodenBucketItem(Fluids.EMPTY, settings),
            new Item.Settings().maxCount(16));
    // A wooden bucket, filled with water
    public static final WoodenBucketItem WOODEN_WATER_BUCKET_ITEM = (WoodenBucketItem) registerItem(
            "wooden_water_bucket",
            settings -> new WoodenBucketItem(Fluids.WATER, settings),
            new Item.Settings().recipeRemainder(WOODEN_BUCKET_ITEM).maxCount(1));
    // Wooden shears
    public static final ShearsItem WOODEN_SHEARS_ITEM = (ShearsItem) registerItem(
            "wooden_shears",
            ShearsItem::new,
            new Item.Settings().maxDamage(3).component(DataComponentTypes.TOOL, ShearsItem.createToolComponent()));

    public static void Register() {
        // Item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(
                entries -> {
                    entries.add(WOODEN_BUCKET_ITEM);
                    entries.add(WOODEN_WATER_BUCKET_ITEM);
                    entries.add(WOODEN_SHEARS_ITEM);
                });
    }
}
