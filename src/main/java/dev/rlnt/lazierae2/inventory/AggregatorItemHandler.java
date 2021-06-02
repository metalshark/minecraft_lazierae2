package dev.rlnt.lazierae2.inventory;

import dev.rlnt.lazierae2.inventory.base.MultiItemHandler;
import dev.rlnt.lazierae2.recipe.type.AggregatorRecipe;
import dev.rlnt.lazierae2.setup.ModRecipes;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import dev.rlnt.lazierae2.util.GameUtil;
import java.util.List;
import net.minecraft.item.ItemStack;

public class AggregatorItemHandler extends MultiItemHandler {

    protected List<AggregatorRecipe> recipes;

    public AggregatorItemHandler(int size, ProcessorTile<?, ?> tile) {
        super(size, tile);
        recipes = GameUtil.getRecipeManager(tile.getLevel()).getAllRecipesFor(ModRecipes.Types.AGGREGATOR);
        fillValids();
    }

    @Override
    protected boolean isInput(ItemStack stack) {
        for (AggregatorRecipe recipe : recipes) {
            if (recipe.getInputs().stream().anyMatch(input -> input.test(stack))) return true;
        }
        return false;
    }

    private void fillValids() {
        for (AggregatorRecipe recipe : recipes) fillRecipeValidSlots(recipe);
    }
}
