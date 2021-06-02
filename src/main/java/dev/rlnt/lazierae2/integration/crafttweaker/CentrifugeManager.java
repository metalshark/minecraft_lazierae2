package dev.rlnt.lazierae2.integration.crafttweaker;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import dev.rlnt.lazierae2.integration.crafttweaker.base.SingleRecipeManager;
import dev.rlnt.lazierae2.recipe.type.CentrifugeRecipe;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@SuppressWarnings("unused")
@ZenRegister
@ZenCodeType.Name("mods." + MOD_ID + ".PulseCentrifuge")
public class CentrifugeManager implements SingleRecipeManager<CentrifugeRecipe> {

    public static final CentrifugeManager INSTANCE = new CentrifugeManager();

    @Override
    public IRecipeType<CentrifugeRecipe> getRecipeType() {
        return ModRecipes.Types.CENTRIFUGE;
    }

    @Override
    public int getDefaultProcessTime() {
        return ModConfig.PROCESSING.centrifugeWorkTicksBase.get();
    }

    @Override
    public CentrifugeRecipe createRecipe(ResourceLocation id, ItemStack output, Ingredient input, int processTime) {
        return new CentrifugeRecipe(id, output, input, processTime);
    }
}
