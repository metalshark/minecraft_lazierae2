package dev.rlnt.lazierae2.recipe.builder;

import static dev.rlnt.lazierae2.Constants.CENTRIFUGE_ID;

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

public class CentrifugeRecipeBuilder extends SingleRecipeBuilder {

    private CentrifugeRecipeBuilder(IItemProvider output, int outputCount) {
        super(output, outputCount);
    }

    public static CentrifugeRecipeBuilder builder(IItemProvider output, int outputCount) {
        return new CentrifugeRecipeBuilder(output, outputCount);
    }

    public CentrifugeRecipeBuilder input(Ingredient input) {
        this.input = input;
        return this;
    }

    public CentrifugeRecipeBuilder input(ITag<Item> tag) {
        return input(Ingredient.of(tag));
    }

    public CentrifugeRecipeBuilder input(IItemProvider item) {
        return input(Ingredient.of(item));
    }

    public CentrifugeRecipeBuilder processingTime(int ticks) {
        processingTime = ticks;
        return this;
    }

    @Override
    protected void build(Consumer<IFinishedRecipe> consumer, ResourceLocation recipeID) {
        consumer.accept(new Recipe(recipeID, this));
    }

    @Override
    protected String getId() {
        return CENTRIFUGE_ID;
    }

    @Override
    protected void checkProcessingTime() {
        if (processingTime == 0) processingTime = ModConfig.PROCESSING.centrifugeWorkTicksBase.get();
    }

    private static class Recipe extends FinishedSingleRecipe<CentrifugeRecipeBuilder> {

        Recipe(ResourceLocation recipeID, CentrifugeRecipeBuilder builder) {
            super(recipeID, builder, CENTRIFUGE_ID);
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getType() {
            return ModRecipes.Serializers.CENTRIFUGE.get();
        }
    }
}
