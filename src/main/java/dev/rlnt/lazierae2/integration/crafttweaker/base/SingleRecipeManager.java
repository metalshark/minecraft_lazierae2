package dev.rlnt.lazierae2.integration.crafttweaker.base;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import dev.rlnt.lazierae2.recipe.type.base.SingleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@SuppressWarnings("unused")
@ZenRegister
@ZenCodeType.Name("mods." + MOD_ID + ".SingleRecipeManager")
public interface SingleRecipeManager<R extends SingleRecipe> extends IRecipeManager {
    /**
     * Adds a new processor recipe from the provided information.
     * @param name the name which is used for the recipe id
     * @param output the output stack
     * @param input the input ingredient
     * @param processTime the optional processing time
     */
    @ZenCodeType.Method
    default void addRecipe(String name, IItemStack output, int processTime, IIngredient input) {
        // use CraftTweaker namespace so people don't complain about the mod being broken
        ResourceLocation id = new ResourceLocation(CraftTweaker.MODID, fixRecipeName(name));
        // map the provided input to a proper type and make sure it's only 1
        Ingredient inputConverted = input.asVanillaIngredient();
        if (inputConverted.getItems()[0].getCount() > 1) inputConverted.getItems()[0].setCount(1);
        // create the recipe from the provided information
        R recipe = createRecipe(id, output.getInternal(), inputConverted, processTime);
        // register the recipe
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }

    /**
     * Adds a new processor recipe from the provided information.
     * This uses the default processing time from the config.
     * @param name the name which is used for the recipe id
     * @param output the output stack
     * @param input the input ingredient
     */
    @ZenCodeType.Method
    default void addRecipe(String name, IItemStack output, IIngredient input) {
        addRecipe(name, output, getDefaultProcessTime(), input);
    }

    int getDefaultProcessTime();

    R createRecipe(ResourceLocation id, ItemStack output, Ingredient input, int processTime);
}
