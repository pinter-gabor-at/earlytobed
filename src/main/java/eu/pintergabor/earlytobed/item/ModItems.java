package eu.pintergabor.earlytobed.item;

import eu.pintergabor.earlytobed.Global;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.material.Fluids;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;


public final class ModItems {
	// A wooden bucket.
	public static WoodenBucketItem WOODEN_BUCKET_ITEM;
	// A wooden bucket, filled with water.
	public static WoodenBucketItem WOODEN_WATER_BUCKET_ITEM;
	// Wooden shears.
	public static ShearsItem WOODEN_SHEARS_ITEM;

	public static void register() {
		// Create and register wooden buckets.
		WOODEN_BUCKET_ITEM = (WoodenBucketItem) Items.registerItem(
			ResourceKey.create(Registries.ITEM, Global.modId("wooden_bucket")),
			settings -> new WoodenBucketItem(Fluids.EMPTY, settings),
			new Item.Properties().stacksTo(16));
		WOODEN_WATER_BUCKET_ITEM = (WoodenBucketItem) Items.registerItem(
			ResourceKey.create(Registries.ITEM, Global.modId("wooden_water_bucket")),
			settings -> new WoodenBucketItem(Fluids.WATER, settings),
			new Item.Properties()
				.craftRemainder(WOODEN_BUCKET_ITEM)
				.stacksTo(1));
		// Create and register wooden shears.
		WOODEN_SHEARS_ITEM = (ShearsItem) Items.registerItem(
			ResourceKey.create(Registries.ITEM, Global.modId("wooden_shears")),
			ShearsItem::new,
			new Item.Properties()
				.durability(3)
				.component(DataComponents.TOOL, ShearsItem.createToolProperties()));
		// Item groups.
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(
			entries -> {
				entries.prepend(WOODEN_BUCKET_ITEM);
				entries.prepend(WOODEN_WATER_BUCKET_ITEM);
				entries.prepend(WOODEN_SHEARS_ITEM);
			});
	}
}
