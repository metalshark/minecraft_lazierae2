package dev.rlnt.lazierae2.inventory.base;

import dev.rlnt.lazierae2.recipe.type.base.MultiRecipe;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public abstract class MultiItemHandler extends AbstractItemHandler {

    private final Set<Ingredient> slot1Valids = new HashSet<>();
    private final Set<Ingredient> slot2Valids = new HashSet<>();
    private final Set<Ingredient> slot3Valids = new HashSet<>();

    protected MultiItemHandler(int size, ProcessorTile<?, ?> tile) {
        super(size, tile);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (Arrays.stream(tile.getInputSlots()).anyMatch(inputSlot -> inputSlot == slot)) return isInput(stack);
        return super.isItemValid(slot, stack);
    }

    @Override
    public int getValidSlot(ItemStack stack) {
        int[] inputSlots = tile.getInputSlots();

        ItemStack slot1Stack = getStackInSlot(inputSlots[0]);
        if (
            (slot1Stack.isEmpty() || stack.sameItem(slot1Stack)) &&
            slot1Valids.stream().anyMatch(element -> element.test(stack))
        ) return inputSlots[0];

        ItemStack slot2Stack = getStackInSlot(inputSlots[1]);
        if (
            (slot2Stack.isEmpty() || stack.sameItem(slot2Stack)) &&
            slot2Valids.stream().anyMatch(element -> element.test(stack))
        ) return inputSlots[1];

        ItemStack slot3Stack = getStackInSlot(inputSlots[2]);
        if (
            (slot3Stack.isEmpty() || stack.sameItem(slot3Stack)) &&
            slot3Valids.stream().anyMatch(element -> element.test(stack))
        ) return inputSlots[2];

        return -1;
    }

    protected void fillRecipeValidSlots(MultiRecipe recipe) {
        NonNullList<Ingredient> inputs = recipe.getInputs();
        if (!inputs.isEmpty()) slot1Valids.add(recipe.getInputs().get(0));
        if (inputs.size() > 1) slot2Valids.add(recipe.getInputs().get(1));
        if (inputs.size() > 2) slot3Valids.add(recipe.getInputs().get(2));
    }

    protected abstract boolean isInput(ItemStack stack);
}
