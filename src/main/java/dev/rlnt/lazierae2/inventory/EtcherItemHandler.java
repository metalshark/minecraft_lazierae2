package dev.rlnt.lazierae2.inventory;

import dev.rlnt.lazierae2.inventory.base.MultiItemHandler;
import dev.rlnt.lazierae2.recipe.type.EtcherRecipe;
import dev.rlnt.lazierae2.setup.ModRecipes;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import dev.rlnt.lazierae2.util.GameUtil;
import java.util.List;
import net.minecraft.item.ItemStack;

public class EtcherItemHandler extends MultiItemHandler {

    protected List<EtcherRecipe> recipes;

    public EtcherItemHandler(int size, ProcessorTile<?, ?> tile) {
        super(size, tile);
        if (tile.hasLevel() && tile.getLevel() != null && !tile.getLevel().isClientSide()) {
            recipes = GameUtil.getRecipeManager(tile.getLevel()).getAllRecipesFor(ModRecipes.Types.ETCHER);
            fillValids();
        }
    }

    @Override
    protected boolean isInput(ItemStack stack) {
        for (EtcherRecipe recipe : recipes) {
            if (recipe.getInputs().stream().anyMatch(input -> input.test(stack))) return true;
        }
        return false;
    }

    private void fillValids() {
        for (EtcherRecipe recipe : recipes) fillRecipeValidSlots(recipe);
    }
}
