package eu.pintergabor.earlytobed.item;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.earlytobed.Global;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;


/**
 * The standard boilerplate to create recipes in {@link ModRecipeGenerator}.
 */
public class ModRecipeRunner extends RecipeProvider.Runner {

	public ModRecipeRunner(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> registries
	) {
		super(output, registries);
	}

	@Override
	protected @NonNull RecipeProvider createRecipeProvider(
		HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output
	) {
		return new ModRecipeGenerator(registries, output);
	}

	@Override
	public @NonNull String getName() {
		return Global.MODID + " recipes";
	}
}
