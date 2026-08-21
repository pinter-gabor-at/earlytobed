package eu.pintergabor.earlytobed.item;

import static net.minecraft.client.data.models.model.ModelTemplates.FLAT_ITEM;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;


public final class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(
		final @NonNull FabricPackOutput output
	) {
		super(output);
	}

	/**
	 * There are no blocks in this mod.
	 */
	@Override
	public void generateBlockStateModels(
		final @NonNull BlockModelGenerators generators
	) {
	}

	/**
	 * Create item models.
	 */
	@Override
	public void generateItemModels(
		final @NonNull ItemModelGenerators generators
	) {
		generators.generateFlatItem(ModItems.WOODEN_BUCKET_ITEM, FLAT_ITEM);
		generators.generateFlatItem(ModItems.WOODEN_WATER_BUCKET_ITEM, FLAT_ITEM);
		generators.generateFlatItem(ModItems.WOODEN_SHEARS_ITEM, FLAT_ITEM);
	}
}
