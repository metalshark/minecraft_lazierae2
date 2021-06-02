package dev.rlnt.lazierae2.integration.crafttweaker;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import dev.rlnt.lazierae2.integration.crafttweaker.base.MultiRecipeManager;
import dev.rlnt.lazierae2.recipe.type.EtcherRecipe;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@SuppressWarnings("unused")
@ZenRegister
@ZenCodeType.Name("mods." + MOD_ID + ".CircuitEtcher")
public class EtcherManager implements MultiRecipeManager<EtcherRecipe> {

    public static final EtcherManager INSTANCE = new EtcherManager();

    @Override
    public IRecipeType<EtcherRecipe> getRecipeType() {
        return ModRecipes.Types.ETCHER;
    }

    @Override
    public int getDefaultProcessTime() {
        return ModConfig.PROCESSING.etcherWorkTicksBase.get();
    }

    @Override
    public EtcherRecipe createRecipe(ResourceLocation id, ItemStack output, Ingredient[] inputs, int processTime) {
        return new EtcherRecipe(id, output, inputs, processTime);
    }
}
