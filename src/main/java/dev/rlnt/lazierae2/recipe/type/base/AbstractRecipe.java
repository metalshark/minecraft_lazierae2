package dev.rlnt.lazierae2.recipe.type.base;

import javax.annotation.Nonnull;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AbstractRecipe implements IRecipe<IInventory> {

    private final ResourceLocation id;
    protected ItemStack output;
    protected int processTime;

    AbstractRecipe(ResourceLocation id) {
        this.id = id;
    }

    public ItemStack getOutput() {
        return output;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack assemble(IInventory inventory) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }
}
