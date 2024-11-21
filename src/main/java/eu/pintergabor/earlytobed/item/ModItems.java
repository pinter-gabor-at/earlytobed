package eu.pintergabor.earlytobed.item;

import eu.pintergabor.earlytobed.Global;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public final class ModItems {
    // A wooden bucket
    public static WoodenBucketItem WOODEN_BUCKET_ITEM;
    // A wooden bucket, filled with water
    public static WoodenBucketItem WOODEN_WATER_BUCKET_ITEM;
    // Wooden shears
    public static ShearsItem WOODEN_SHEARS_ITEM;

    public static void Register() {
        // Create and register wooden buckets
        WOODEN_BUCKET_ITEM = (WoodenBucketItem) Items.register(
                RegistryKey.of(RegistryKeys.ITEM, Global.ModIdentifier("wooden_bucket")),
                settings -> new WoodenBucketItem(Fluids.EMPTY, settings),
                new Item.Settings().maxCount(16));
        WOODEN_WATER_BUCKET_ITEM = (WoodenBucketItem) Items.register(
                RegistryKey.of(RegistryKeys.ITEM, Global.ModIdentifier("wooden_water_bucket")),
                settings -> new WoodenBucketItem(Fluids.WATER, settings),
                new Item.Settings().recipeRemainder(WOODEN_BUCKET_ITEM).maxCount(1));
        // Create and register wooden shears
        WOODEN_SHEARS_ITEM = (ShearsItem) Items.register(
                RegistryKey.of(RegistryKeys.ITEM, Global.ModIdentifier("wooden_shears")),
                ShearsItem::new,
                new Item.Settings().maxDamage(3).component(DataComponentTypes.TOOL, ShearsItem.createToolComponent()));
        // Item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(
                entries -> {
                    entries.add(WOODEN_BUCKET_ITEM);
                    entries.add(WOODEN_WATER_BUCKET_ITEM);
                    entries.add(WOODEN_SHEARS_ITEM);
                });
    }
}
