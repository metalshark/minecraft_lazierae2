package dev.rlnt.lazierae2.integration.crafttweaker;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import dev.rlnt.lazierae2.integration.crafttweaker.base.SingleRecipeManager;
import dev.rlnt.lazierae2.recipe.type.EnergizerRecipe;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@SuppressWarnings("unused")
@ZenRegister
@ZenCodeType.Name("mods." + MOD_ID + ".CrystalEnergizer")
public class EnergizerManager implements SingleRecipeManager<EnergizerRecipe> {

    public static final EnergizerManager INSTANCE = new EnergizerManager();

    @Override
    public IRecipeType<EnergizerRecipe> getRecipeType() {
        return ModRecipes.Types.ENERGIZER;
    }

    @Override
    public int getDefaultProcessTime() {
        return ModConfig.PROCESSING.energizerWorkTicksBase.get();
    }

    @Override
    public EnergizerRecipe createRecipe(ResourceLocation id, ItemStack output, Ingredient input, int processTime) {
        return new EnergizerRecipe(id, output, input, processTime);
    }
}
