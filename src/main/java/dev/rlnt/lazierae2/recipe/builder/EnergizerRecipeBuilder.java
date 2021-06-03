package dev.rlnt.lazierae2.recipe.builder;

import static dev.rlnt.lazierae2.Constants.ENERGIZER_ID;

import dev.rlnt.lazierae2.recipe.builder.base.FinishedSingleRecipe;
import dev.rlnt.lazierae2.recipe.builder.base.SingleRecipeBuilder;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModRecipes;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class EnergizerRecipeBuilder extends SingleRecipeBuilder {

    private EnergizerRecipeBuilder(IItemProvider output, int outputCount) {
        super(output, outputCount);
    }

    public static EnergizerRecipeBuilder builder(IItemProvider output, int outputCount) {
        return new EnergizerRecipeBuilder(output, outputCount);
    }

    public EnergizerRecipeBuilder input(Ingredient input) {
        this.input = input;
        return this;
    }

    public EnergizerRecipeBuilder input(ITag<Item> tag) {
        return input(Ingredient.of(tag));
    }

    public EnergizerRecipeBuilder input(IItemProvider item) {
        return input(Ingredient.of(item));
    }

    public EnergizerRecipeBuilder processingTime(int ticks) {
        processingTime = ticks;
        return this;
    }

    @Override
    protected void build(Consumer<IFinishedRecipe> consumer, ResourceLocation recipeID) {
        consumer.accept(new Recipe(recipeID, this));
    }

    @Override
    protected String getId() {
        return ENERGIZER_ID;
    }

    @Override
    protected void checkProcessingTime() {
        if (processingTime == 0) processingTime = ModConfig.PROCESSING.energizerWorkTicksBase.get();
    }

    private static class Recipe extends FinishedSingleRecipe<EnergizerRecipeBuilder> {

        Recipe(ResourceLocation recipeID, EnergizerRecipeBuilder builder) {
            super(recipeID, builder, ENERGIZER_ID);
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getType() {
            return ModRecipes.Serializers.ENERGIZER.get();
        }
    }
}
