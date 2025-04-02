package eu.pintergabor.earlytobed.item;

import eu.pintergabor.earlytobed.Global;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Global.MODID, value = Dist.CLIENT)
public final class ModItems {
	// Only used in ModCommon for registration.
	public static final DeferredRegister.Items ITEMS =
		DeferredRegister.createItems(Global.MODID);
	// A wooden bucket.
	public static final DeferredItem<Item> WOODEN_BUCKET_ITEM =
		ITEMS.registerItem("wooden_bucket",
			properties -> new WoodenBucketItem(Fluids.EMPTY, properties),
			new Item.Properties().stacksTo(16));
	// A wooden bucket, filled with water.
	public static final DeferredItem<Item> WOODEN_WATER_BUCKET_ITEM =
		ITEMS.registerItem("wooden_water_bucket",
			properties -> new WoodenBucketItem(Fluids.WATER, properties),
			new Item.Properties()
				.stacksTo(1));
	// Wooden shears.
	public static final DeferredItem<Item> WOODEN_SHEARS_ITEM =
		ITEMS.registerItem("wooden_shears",
			WoodenShearsItem::new,
			new Item.Properties()
				.durability(3));

	/**
	 * Add items to creative tabs.
	 */
	@SubscribeEvent
	public static void creativeTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(WOODEN_BUCKET_ITEM);
			event.accept(WOODEN_WATER_BUCKET_ITEM);
			event.accept(WOODEN_SHEARS_ITEM);
		}
	}
}
