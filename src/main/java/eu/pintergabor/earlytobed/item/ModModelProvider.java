package eu.pintergabor.earlytobed.item;

import static net.minecraft.client.data.models.model.ModelTemplates.FLAT_ITEM;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;


public class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(ModItems.WOODEN_BUCKET_ITEM, FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.WOODEN_WATER_BUCKET_ITEM, FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.WOODEN_SHEARS_ITEM, FLAT_ITEM);
	}
}
