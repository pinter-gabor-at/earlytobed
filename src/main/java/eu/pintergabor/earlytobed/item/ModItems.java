package eu.pintergabor.earlytobed.item;

import static eu.pintergabor.earlytobed.Global.modId;

import java.util.function.Function;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.material.Fluids;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;


public final class ModItems {
	// A wooden bucket.
	public static WoodenBucketItem WOODEN_BUCKET_ITEM;
	// A wooden bucket, filled with water.
	public static WoodenBucketItem WOODEN_WATER_BUCKET_ITEM;
	// Wooden shears.
	public static ShearsItem WOODEN_SHEARS_ITEM;

	/**
	 * Create and register an item similarly as it is in {@link Items}.
	 *
	 * @param name        Name of the item, without modId.
	 * @param itemFactory Function to create the item.
	 * @param props       Initial properties.
	 * @param <T>         Subclass of {@link Item}.
	 * @return The created and registered item.
	 */
	public static @NonNull <T extends Item> T registerItem(
		final @NonNull String name,
		final @NonNull Function<Item.Properties, T> itemFactory,
		final Item.@NonNull Properties props
	) {
		// Create the item key.
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, modId(name));
		// Create the item instance.
		T item = itemFactory.apply(props.setId(key));
		// Register the item and return the registered item.
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	/**
	 * Create and register mod items.
	 */
	public static void register() {
		// Create and register wooden buckets.
		WOODEN_BUCKET_ITEM = registerItem(
			"wooden_bucket",
			props -> new WoodenBucketItem(Fluids.EMPTY, props),
			new Item.Properties().stacksTo(16));
		WOODEN_WATER_BUCKET_ITEM = registerItem(
			"wooden_water_bucket",
			props -> new WoodenBucketItem(Fluids.WATER, props),
			new Item.Properties()
				.craftRemainder(WOODEN_BUCKET_ITEM)
				.stacksTo(1));
		// Create and register wooden shears.
		WOODEN_SHEARS_ITEM = registerItem(
			"wooden_shears",
			ShearsItem::new,
			new Item.Properties()
				.durability(3)
				.component(DataComponents.TOOL, ShearsItem.createToolProperties()));
		// Item groups.
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
			.register(
				entries -> {
					entries.prepend(WOODEN_BUCKET_ITEM);
					entries.prepend(WOODEN_WATER_BUCKET_ITEM);
					entries.prepend(WOODEN_SHEARS_ITEM);
				});
	}
}
