package eu.pintergabor.earlytobed.item;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

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
