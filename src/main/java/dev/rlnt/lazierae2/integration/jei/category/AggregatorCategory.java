package dev.rlnt.lazierae2.integration.jei.category;

import static dev.rlnt.lazierae2.Constants.AGGREGATOR_ID;
import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.integration.jei.category.base.ModRecipeCategory;
import dev.rlnt.lazierae2.recipe.type.AggregatorRecipe;
import dev.rlnt.lazierae2.screen.AggregatorScreen;
import dev.rlnt.lazierae2.setup.ModBlocks;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class AggregatorCategory extends ModRecipeCategory<AggregatorRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(MOD_ID, AGGREGATOR_ID);
    private static final int TEXTURE_WIDTH = 113;
    private static final int TEXTURE_HEIGHT = 42;
    private static final int TEXTURE_OFFSET_U = 32;
    private static final int TEXTURE_OFFSET_V = 22;
    private static final int PROGRESS_BAR_OFFSET_U = 52;
    private static final int PROGRESS_BAR_OFFSET_V = 14;

    public AggregatorCategory(IGuiHelper guiHelper) {
        super(guiHelper, AGGREGATOR_ID);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends AggregatorRecipe> getRecipeClass() {
        return AggregatorRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AggregatorRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        // output slot
        initSlot(itemStackGroup, 1, false, 88, 13);
        // input slots
        initSlot(itemStackGroup, 2, true, 8, 3);
        initSlot(itemStackGroup, 3, true, 28, 13);
        initSlot(itemStackGroup, 4, true, 8, 23);
        // set ingredients defined in setIngredients()
        itemStackGroup.set(ingredients);
    }

    @Override
    protected int getAtlasWidth() {
        return AggregatorScreen.ATLAS_WIDTH;
    }

    @Override
    protected int getTextureWidth() {
        return TEXTURE_WIDTH;
    }

    @Override
    protected int getTextureHeight() {
        return TEXTURE_HEIGHT;
    }

    @Override
    protected int getTextureOffsetU() {
        return TEXTURE_OFFSET_U;
    }

    @Override
    protected int getTextureOffsetV() {
        return TEXTURE_OFFSET_V;
    }

    @Override
    protected int getProgressBarWidth() {
        return AggregatorScreen.PROGRESS_BAR_WIDTH;
    }

    @Override
    protected int getProgressBarHeight() {
        return AggregatorScreen.PROGRESS_BAR_HEIGHT;
    }

    @Override
    protected int getProgressBarOffsetU() {
        return PROGRESS_BAR_OFFSET_U;
    }

    @Override
    protected int getProgressBarOffsetV() {
        return PROGRESS_BAR_OFFSET_V;
    }

    @Override
    protected IItemProvider getIconProvider() {
        return ModBlocks.AGGREGATOR.get();
    }
}
