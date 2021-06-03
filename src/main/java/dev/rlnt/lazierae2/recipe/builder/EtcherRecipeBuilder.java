package dev.rlnt.lazierae2.recipe.builder;

import static dev.rlnt.lazierae2.Constants.ETCHER_ID;

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

public class EtcherRecipeBuilder extends MultiRecipeBuilder {

    private EtcherRecipeBuilder(IItemProvider output, int outputCount) {
        super(output, outputCount);
    }

    public static EtcherRecipeBuilder builder(IItemProvider output, int outputCount) {
        return new EtcherRecipeBuilder(output, outputCount);
    }

    public EtcherRecipeBuilder input(Ingredient input) {
        if (inputs.size() < 3) inputs.add(input);
        return this;
    }

    public EtcherRecipeBuilder input(ITag<Item> tag) {
        return input(Ingredient.of(tag));
    }

    public EtcherRecipeBuilder input(IItemProvider item) {
        return input(Ingredient.of(item));
    }

    public EtcherRecipeBuilder processingTime(int ticks) {
        processingTime = ticks;
        return this;
    }

    @Override
    protected void build(Consumer<IFinishedRecipe> consumer, ResourceLocation recipeID) {
        consumer.accept(new Recipe(recipeID, this));
    }

    @Override
    protected String getId() {
        return ETCHER_ID;
    }

    @Override
    protected void checkProcessingTime() {
        if (processingTime == 0) processingTime = ModConfig.PROCESSING.etcherWorkTicksBase.get();
    }

    private static class Recipe extends FinishedMultiRecipe<EtcherRecipeBuilder> {

        private Recipe(ResourceLocation recipeID, EtcherRecipeBuilder builder) {
            super(recipeID, builder, ETCHER_ID);
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getType() {
            return ModRecipes.Serializers.ETCHER.get();
        }
    }
}
