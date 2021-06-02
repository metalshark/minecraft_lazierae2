package dev.rlnt.lazierae2.recipe.builder.base;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;

public abstract class MultiRecipeBuilder extends AbstractRecipeBuilder {

    protected final NonNullList<Ingredient> inputs = NonNullList.create();

    protected MultiRecipeBuilder(IItemProvider output, int outputCount) {
        super(output, outputCount);
    }
}
