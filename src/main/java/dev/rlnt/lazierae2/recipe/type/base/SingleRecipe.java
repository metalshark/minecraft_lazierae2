package dev.rlnt.lazierae2.recipe.type.base;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class SingleRecipe extends AbstractRecipe {

    protected Ingredient input;

    protected SingleRecipe(ResourceLocation id) {
        super(id);
    }

    public Ingredient getInput() {
        return input;
    }

    public void setInput(Ingredient input) {
        this.input = input;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return input.test(inv.getItem(2));
    }
}
