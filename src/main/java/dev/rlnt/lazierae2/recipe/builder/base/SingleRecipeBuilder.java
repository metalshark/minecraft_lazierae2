package dev.rlnt.lazierae2.recipe.builder.base;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;

public abstract class SingleRecipeBuilder extends AbstractRecipeBuilder {

    protected Ingredient input = Ingredient.EMPTY;

    protected SingleRecipeBuilder(IItemProvider output, int outputCount) {
        super(output, outputCount);
    }
}
