package eu.pintergabor.earlytobed;

import eu.pintergabor.earlytobed.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;


@Mod(Global.MODID)
public final class ModCommon {

	public ModCommon(IEventBus modEventBus, ModContainer modContainer) {
		// Register items.
		ModItems.ITEMS.register(modEventBus);
	}
}
