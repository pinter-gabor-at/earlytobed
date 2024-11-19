package eu.pintergabor.earlytobed.item;

import eu.pintergabor.earlytobed.Global;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.TOOLS, ModItems.WOODEN_BUCKET_ITEM)
                        .pattern("   ")
                        .pattern("W W")
                        .pattern(" W ")
                        .input('W', ItemTags.LOGS)
                        .criterion("has_" + ItemTags.LOGS, conditionsFromTag(ItemTags.LOGS))
                        .offerTo(exporter);
                createShaped(RecipeCategory.TOOLS, ModItems.WOODEN_SHEARS_ITEM)
                        .pattern("   ")
                        .pattern(" W ")
                        .pattern("W  ")
                        .input('W', ItemTags.LOGS)
                        .criterion("has_" + ItemTags.LOGS, conditionsFromTag(ItemTags.LOGS))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return Global.MODID + " recipes";
    }
}
