package eu.pintergabor.earlytobed.item;

import eu.pintergabor.earlytobed.Global;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output,
                              CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WOODEN_BUCKET_ITEM)
                .pattern("   ")
                .pattern("W W")
                .pattern(" W ")
                .input('W', ItemTags.LOGS)
                .criterion("has_" + ItemTags.LOGS, conditionsFromTag(ItemTags.LOGS))
                .offerTo(exporter, Global.ModIdentifier(getRecipeName(ModItems.WOODEN_BUCKET_ITEM)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WOODEN_SHEARS_ITEM)
                .pattern("   ")
                .pattern(" W ")
                .pattern("W  ")
                .input('W', ItemTags.LOGS)
                .criterion("has_" + ItemTags.LOGS, conditionsFromTag(ItemTags.LOGS))
                .offerTo(exporter, Global.ModIdentifier(getRecipeName(ModItems.WOODEN_SHEARS_ITEM)));
    }
}
