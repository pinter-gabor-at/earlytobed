package eu.pintergabor.earlytobed.item;

import static net.minecraft.client.data.models.model.ModelTemplates.FLAT_ITEM;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;


public final class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	/**
	 * There are no blocks in this mod.
	 */
	@Override
	public void generateBlockStateModels(@NonNull BlockModelGenerators blockStateModelGenerator) {
	}

	/**
	 * Create item models.
	 */
	@Override
	public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(ModItems.WOODEN_BUCKET_ITEM, FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.WOODEN_WATER_BUCKET_ITEM, FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.WOODEN_SHEARS_ITEM, FLAT_ITEM);
	}
}
