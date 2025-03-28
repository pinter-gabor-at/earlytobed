package eu.pintergabor.earlytobed.item;

import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;


public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModItems.WOODEN_BUCKET_ITEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.WOODEN_WATER_BUCKET_ITEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.WOODEN_SHEARS_ITEM, Models.GENERATED);
	}
}
