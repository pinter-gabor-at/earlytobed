package eu.pintergabor.earlytobed;

import eu.pintergabor.earlytobed.item.ModModelProvider;
import eu.pintergabor.earlytobed.item.ModRecipeRunner;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;


@EventBusSubscriber(modid = Global.MODID, value = Dist.CLIENT)
public class ModDataGenerator {

	@SubscribeEvent
	public static void init(GatherDataEvent.@NonNull Client event) {
		// Create models.
		event.createProvider(ModModelProvider::new);
		// Create recipes.
		event.createProvider(ModRecipeRunner::new);
	}
}
