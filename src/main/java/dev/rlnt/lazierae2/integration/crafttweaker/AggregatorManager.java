package dev.rlnt.lazierae2.integration.crafttweaker;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import dev.rlnt.lazierae2.integration.crafttweaker.base.MultiRecipeManager;
import dev.rlnt.lazierae2.recipe.type.AggregatorRecipe;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@SuppressWarnings("unused")
@ZenRegister
@ZenCodeType.Name("mods." + MOD_ID + ".FluixAggregator")
public class AggregatorManager implements MultiRecipeManager<AggregatorRecipe> {

    public static final AggregatorManager INSTANCE = new AggregatorManager();

    @Override
    public IRecipeType<AggregatorRecipe> getRecipeType() {
        return ModRecipes.Types.AGGREGATOR;
    }

    @Override
    public int getDefaultProcessTime() {
        return ModConfig.PROCESSING.aggregatorWorkTicksBase.get();
    }

    @Override
    public AggregatorRecipe createRecipe(ResourceLocation id, ItemStack output, Ingredient[] inputs, int processTime) {
        return new AggregatorRecipe(id, output, inputs, processTime);
    }
}
