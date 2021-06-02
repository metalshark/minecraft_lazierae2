package dev.rlnt.lazierae2.recipe.type.base;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class MultiRecipe extends AbstractRecipe {

    public final NonNullList<Ingredient> inputs = NonNullList.create();

    protected MultiRecipe(ResourceLocation id) {
        super(id);
    }

    public NonNullList<Ingredient> getInputs() {
        return inputs;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        List<ItemStack> containerItems = new ArrayList<>();
        int[] inputSlots = { 2, 3, 4 };
        for (int inputSlot : inputSlots) {
            ItemStack item = inv.getItem(inputSlot);
            if (item.sameItem(ItemStack.EMPTY)) continue;
            containerItems.add(item);
        }

        if (inputs.size() != containerItems.size()) return false;

        int found = 0;
        for (Ingredient input : inputs) {
            for (int j = 0; j < containerItems.size(); j++) {
                if (input.test(containerItems.get(j))) {
                    containerItems.remove(j);
                    found++;
                    break;
                }
            }
        }

        return found == inputs.size();
    }
}
