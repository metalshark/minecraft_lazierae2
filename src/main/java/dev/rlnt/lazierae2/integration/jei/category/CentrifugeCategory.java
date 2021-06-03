package dev.rlnt.lazierae2.integration.jei.category;

import static dev.rlnt.lazierae2.Constants.CENTRIFUGE_ID;
import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.integration.jei.category.base.ModRecipeCategory;
import dev.rlnt.lazierae2.recipe.type.CentrifugeRecipe;
import dev.rlnt.lazierae2.screen.CentrifugeScreen;
import dev.rlnt.lazierae2.setup.ModBlocks;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class CentrifugeCategory extends ModRecipeCategory<CentrifugeRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(MOD_ID, CENTRIFUGE_ID);
    private static final int TEXTURE_WIDTH = 90;
    private static final int TEXTURE_HEIGHT = 34;
    private static final int TEXTURE_OFFSET_U = 51;
    private static final int TEXTURE_OFFSET_V = 26;
    private static final int PROGRESS_BAR_OFFSET_U = 29;
    private static final int PROGRESS_BAR_OFFSET_V = 10;

    public CentrifugeCategory(IGuiHelper guiHelper) {
        super(guiHelper, CENTRIFUGE_ID);
        IDrawableStatic progressBarTexture = guiHelper
            .drawableBuilder(texture, 178, 0, CentrifugeScreen.PROGRESS_BAR_WIDTH, CentrifugeScreen.PROGRESS_BAR_HEIGHT)
            .setTextureSize(CentrifugeScreen.ATLAS_WIDTH, ATLAS_HEIGHT)
            .build();
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends CentrifugeRecipe> getRecipeClass() {
        return CentrifugeRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CentrifugeRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        // output slot
        initSlot(itemStackGroup, 1, false, 65, 9);
        // input slot
        initSlot(itemStackGroup, 2, true, 5, 9);
        // set ingredients defined in setIngredients()
        itemStackGroup.set(ingredients);
    }

    @Override
    protected int getAtlasWidth() {
        return CentrifugeScreen.ATLAS_WIDTH;
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
        return CentrifugeScreen.PROGRESS_BAR_WIDTH;
    }

    @Override
    protected int getProgressBarHeight() {
        return CentrifugeScreen.PROGRESS_BAR_HEIGHT;
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
        return ModBlocks.CENTRIFUGE.get();
    }
}
