package eu.pintergabor.earlytobed;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.earlytobed.item.ModModelProvider;
import eu.pintergabor.earlytobed.item.ModRecipeRunner;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Global.MODID, value = Dist.CLIENT)
public class DataGen {

	@SubscribeEvent
	public static void init(GatherDataEvent.Client event) {
		// Create recipes.
		final DataGenerator generator = event.getGenerator();
		final PackOutput output = generator.getPackOutput();
		final CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		event.addProvider(new ModModelProvider(output, Global.MODID));
		event.addProvider(new ModRecipeRunner(output, lookupProvider));
	}
}
