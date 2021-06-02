package dev.rlnt.lazierae2.inventory;

import dev.rlnt.lazierae2.inventory.base.SingleItemHandler;
import dev.rlnt.lazierae2.recipe.type.EnergizerRecipe;
import dev.rlnt.lazierae2.setup.ModRecipes;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import dev.rlnt.lazierae2.util.GameUtil;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;

public class EnergizerItemHandler extends SingleItemHandler {

    public EnergizerItemHandler(int size, ProcessorTile<?, ?> tile) {
        super(size, tile);
    }

    @Override
    protected boolean isInput(ItemStack stack) {
        RecipeManager recipeManager = GameUtil.getRecipeManager(tile.getLevel());
        List<EnergizerRecipe> recipes = recipeManager.getAllRecipesFor(ModRecipes.Types.ENERGIZER);
        for (EnergizerRecipe recipe : recipes) {
            if (recipe.getInput().test(stack)) return true;
        }
        return false;
    }
}
