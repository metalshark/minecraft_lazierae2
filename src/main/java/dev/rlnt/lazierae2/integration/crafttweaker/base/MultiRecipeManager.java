package dev.rlnt.lazierae2.integration.crafttweaker.base;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import dev.rlnt.lazierae2.recipe.type.base.MultiRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@SuppressWarnings("unused")
@ZenRegister
@ZenCodeType.Name("mods." + MOD_ID + ".MultiRecipeManager")
public interface MultiRecipeManager<R extends MultiRecipe> extends IRecipeManager {
    /**
     * Adds a new processor recipe from the provided information.
     * @param name the name which is used for the recipe id
     * @param output the output stack
     * @param inputs the input ingredients as array
     * @param processTime the optional processing time
     */
    @ZenCodeType.Method
    default void addRecipe(String name, IItemStack output, int processTime, IIngredient[] inputs) {
        // use CraftTweaker namespace so people don't complain about the mod being broken
        ResourceLocation id = new ResourceLocation(CraftTweaker.MODID, fixRecipeName(name));
        // map the provided inputs to a proper type and make sure it's only 1 of each
        Ingredient[] inputsConverted = new Ingredient[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            Ingredient currentInput = inputs[i].asVanillaIngredient();
            if (currentInput.getItems()[0].getCount() > 1) currentInput.getItems()[0].setCount(1);
            inputsConverted[i] = currentInput;
        }
        // create the recipe from the provided information
        R recipe = createRecipe(id, output.getInternal(), inputsConverted, processTime);
        // register the recipe
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }

    /**
     * Adds a new processor recipe from the provided information.
     * This uses the default processing time from the config.
     * @param name the name which is used for the recipe id
     * @param output the output stack
     * @param inputs the input ingredients as array
     */
    @ZenCodeType.Method
    default void addRecipe(String name, IItemStack output, IIngredient[] inputs) {
        addRecipe(name, output, getDefaultProcessTime(), inputs);
    }

    int getDefaultProcessTime();

    R createRecipe(ResourceLocation id, ItemStack output, Ingredient[] inputs, int processTime);
}
