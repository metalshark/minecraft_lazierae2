package dev.rlnt.lazierae2.recipe.type;

import com.google.gson.JsonObject;
import dev.rlnt.lazierae2.recipe.type.base.MultiRecipe;
import dev.rlnt.lazierae2.setup.ModRecipes;
import dev.rlnt.lazierae2.util.RecipeUtil;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class EtcherRecipe extends MultiRecipe {

    EtcherRecipe(ResourceLocation id) {
        super(id);
    }

    public EtcherRecipe(ResourceLocation id, ItemStack output, Ingredient[] inputs, int processTime) {
        this(id);
        this.output = output;
        this.inputs.clear();
        this.inputs.addAll(Arrays.asList(inputs));
        this.processTime = processTime;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.ETCHER.get();
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.ETCHER;
    }

    public static class Serializer
        extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<EtcherRecipe> {

        @Nonnull
        @Override
        public EtcherRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
            EtcherRecipe recipe = new EtcherRecipe(recipeID);
            return (EtcherRecipe) RecipeUtil.fromJSON(json, recipe);
        }

        @Nullable
        @Override
        public EtcherRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer buffer) {
            EtcherRecipe recipe = new EtcherRecipe(recipeID);
            return (EtcherRecipe) RecipeUtil.fromNetwork(buffer, recipe);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, EtcherRecipe recipe) {
            RecipeUtil.toNetwork(buffer, recipe);
        }
    }
}
