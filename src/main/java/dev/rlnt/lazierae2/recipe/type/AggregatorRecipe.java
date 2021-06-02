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

public class AggregatorRecipe extends MultiRecipe {

    AggregatorRecipe(ResourceLocation id) {
        super(id);
    }

    public AggregatorRecipe(ResourceLocation id, ItemStack output, Ingredient[] inputs, int processTime) {
        this(id);
        this.output = output;
        this.inputs.clear();
        this.inputs.addAll(Arrays.asList(inputs));
        this.processTime = processTime;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.AGGREGATOR.get();
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.AGGREGATOR;
    }

    public static class Serializer
        extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<AggregatorRecipe> {

        @Nonnull
        @Override
        public AggregatorRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
            AggregatorRecipe recipe = new AggregatorRecipe(recipeID);
            return (AggregatorRecipe) RecipeUtil.fromJSON(json, recipe);
        }

        @Nullable
        @Override
        public AggregatorRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer buffer) {
            AggregatorRecipe recipe = new AggregatorRecipe(recipeID);
            return (AggregatorRecipe) RecipeUtil.fromNetwork(buffer, recipe);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, AggregatorRecipe recipe) {
            RecipeUtil.toNetwork(buffer, recipe);
        }
    }
}
