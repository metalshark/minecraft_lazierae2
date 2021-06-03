package dev.rlnt.lazierae2.recipe.type;

import com.google.gson.JsonObject;
import dev.rlnt.lazierae2.recipe.type.base.SingleRecipe;
import dev.rlnt.lazierae2.setup.ModRecipes;
import dev.rlnt.lazierae2.util.RecipeUtil;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class EnergizerRecipe extends SingleRecipe {

    EnergizerRecipe(ResourceLocation id) {
        super(id);
    }

    public EnergizerRecipe(ResourceLocation id, ItemStack output, Ingredient input, int processTime) {
        this(id);
        this.output = output;
        this.input = input;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return input.test(inv.getItem(2));
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.Serializers.ENERGIZER.get();
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.ENERGIZER;
    }

    public static class Serializer
        extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<EnergizerRecipe> {

        @Nonnull
        @Override
        public EnergizerRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
            EnergizerRecipe recipe = new EnergizerRecipe(recipeID);
            return (EnergizerRecipe) RecipeUtil.fromJSON(json, recipe);
        }

        @Nullable
        @Override
        public EnergizerRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer buffer) {
            EnergizerRecipe recipe = new EnergizerRecipe(recipeID);
            return (EnergizerRecipe) RecipeUtil.fromNetwork(buffer, recipe);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, EnergizerRecipe recipe) {
            RecipeUtil.toNetwork(buffer, recipe);
        }
    }
}
