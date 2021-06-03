package dev.rlnt.lazierae2.recipe.builder;

import static dev.rlnt.lazierae2.Constants.AGGREGATOR_ID;

import dev.rlnt.lazierae2.recipe.builder.base.FinishedMultiRecipe;
import dev.rlnt.lazierae2.recipe.builder.base.MultiRecipeBuilder;
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

public class AggregatorRecipeBuilder extends MultiRecipeBuilder {

    private AggregatorRecipeBuilder(IItemProvider output, int outputCount) {
        super(output, outputCount);
    }

    public static AggregatorRecipeBuilder builder(IItemProvider output, int outputCount) {
        return new AggregatorRecipeBuilder(output, outputCount);
    }

    public AggregatorRecipeBuilder input(Ingredient input) {
        if (inputs.size() < 3) inputs.add(input);
        return this;
    }

    public AggregatorRecipeBuilder input(ITag<Item> tag) {
        return input(Ingredient.of(tag));
    }

    public AggregatorRecipeBuilder input(IItemProvider item) {
        return input(Ingredient.of(item));
    }

    public AggregatorRecipeBuilder processingTime(int ticks) {
        processingTime = ticks;
        return this;
    }

    @Override
    protected void build(Consumer<IFinishedRecipe> consumer, ResourceLocation recipeID) {
        consumer.accept(new Recipe(recipeID, this));
    }

    @Override
    protected String getId() {
        return AGGREGATOR_ID;
    }

    @Override
    protected void checkProcessingTime() {
        if (processingTime == 0) processingTime = ModConfig.PROCESSING.aggregatorWorkTicksBase.get();
    }

    private static class Recipe extends FinishedMultiRecipe<AggregatorRecipeBuilder> {

        private Recipe(ResourceLocation recipeID, AggregatorRecipeBuilder builder) {
            super(recipeID, builder, AGGREGATOR_ID);
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getType() {
            return ModRecipes.Serializers.AGGREGATOR.get();
        }
    }
}
