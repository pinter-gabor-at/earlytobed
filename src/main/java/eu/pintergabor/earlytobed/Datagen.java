package eu.pintergabor.earlytobed;

import eu.pintergabor.earlytobed.item.ModModelProvider;
import eu.pintergabor.earlytobed.item.ModRecipeRunner;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


public class Datagen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeRunner::new);
	}
}
