package eu.pintergabor.earlytobed.item;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;


public class ModRecipeGenerator extends RecipeProvider {

	protected ModRecipeGenerator(HolderLookup.Provider registries, RecipeOutput output) {
		super(registries, output);
	}

	/**
	 * Generate recipes.
	 */
	@Override
	public void buildRecipes() {
		shaped(RecipeCategory.TOOLS, ModItems.WOODEN_BUCKET_ITEM)
			.pattern("   ")
			.pattern("W W")
			.pattern(" W ")
			.define('W', ItemTags.LOGS)
			.unlockedBy("has_" + ItemTags.LOGS, has(ItemTags.LOGS))
			.save(output);
		shaped(RecipeCategory.TOOLS, ModItems.WOODEN_SHEARS_ITEM)
			.pattern("   ")
			.pattern(" W ")
			.pattern("W  ")
			.define('W', ItemTags.LOGS)
			.unlockedBy("has_" + ItemTags.LOGS, has(ItemTags.LOGS))
			.save(output);
	}
}
