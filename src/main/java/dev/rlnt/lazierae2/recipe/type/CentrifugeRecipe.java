package dev.rlnt.lazierae2.recipe.type;

import com.google.gson.JsonObject;
import dev.rlnt.lazierae2.recipe.type.base.SingleRecipe;
import dev.rlnt.lazierae2.setup.ModRecipes;
import dev.rlnt.lazierae2.util.RecipeUtil;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class CentrifugeRecipe extends SingleRecipe {

    CentrifugeRecipe(ResourceLocation id) {
        super(id);
    }

    public CentrifugeRecipe(ResourceLocation id, ItemStack output, Ingredient input, int processTime) {
        this(id);
        this.output = output;
        this.input = input;
        this.processTime = processTime;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.Serializers.CENTRIFUGE.get();
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.CENTRIFUGE;
    }

    public static class Serializer
        extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<CentrifugeRecipe> {

        @Nonnull
        @Override
        public CentrifugeRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
            CentrifugeRecipe recipe = new CentrifugeRecipe(recipeID);
            return (CentrifugeRecipe) RecipeUtil.fromJSON(json, recipe);
        }

        @Nullable
        @Override
        public CentrifugeRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer buffer) {
            CentrifugeRecipe recipe = new CentrifugeRecipe(recipeID);
            return (CentrifugeRecipe) RecipeUtil.fromNetwork(buffer, recipe);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, CentrifugeRecipe recipe) {
            RecipeUtil.toNetwork(buffer, recipe);
        }
    }
}
