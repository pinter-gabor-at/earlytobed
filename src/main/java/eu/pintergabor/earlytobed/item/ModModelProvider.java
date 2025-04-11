package eu.pintergabor.earlytobed.item;

import static net.minecraft.client.data.models.model.ModelTemplates.FLAT_ITEM;

import eu.pintergabor.earlytobed.Global;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;


public class ModModelProvider extends ModelProvider {

	public ModModelProvider(PackOutput output) {
		super(output, Global.MODID);
	}

	/**
	 * Create item models.
	 */
	@Override
	protected void registerModels(
		@NotNull BlockModelGenerators blockModels,
		@NotNull ItemModelGenerators itemModels) {
		super.registerModels(blockModels, itemModels);
		itemModels.generateFlatItem(ModItems.WOODEN_BUCKET_ITEM.asItem(), FLAT_ITEM);
		itemModels.generateFlatItem(ModItems.WOODEN_WATER_BUCKET_ITEM.asItem(), FLAT_ITEM);
		itemModels.generateFlatItem(ModItems.WOODEN_SHEARS_ITEM.asItem(), FLAT_ITEM);
	}
}
