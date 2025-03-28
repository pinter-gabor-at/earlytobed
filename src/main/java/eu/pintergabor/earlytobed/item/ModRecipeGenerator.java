package eu.pintergabor.earlytobed.item;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;


public class ModRecipeGenerator extends RecipeGenerator {
	protected ModRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
		super(registries, exporter);
	}

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
}
