package eu.pintergabor.earlytobed;

import eu.pintergabor.earlytobed.item.ModModelProvider;
import eu.pintergabor.earlytobed.item.ModRecipeRunner;
import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;


public final class ModDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(
		final @NonNull FabricDataGenerator generator
	) {
		Pack pack = generator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeRunner::new);
	}
}
