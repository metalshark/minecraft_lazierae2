package dev.rlnt.lazierae2.integration.jei.category;

import static dev.rlnt.lazierae2.Constants.ETCHER_ID;
import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.integration.jei.category.base.ModRecipeCategory;
import dev.rlnt.lazierae2.recipe.type.EtcherRecipe;
import dev.rlnt.lazierae2.screen.EtcherScreen;
import dev.rlnt.lazierae2.setup.ModBlocks;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class EtcherCategory extends ModRecipeCategory<EtcherRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(MOD_ID, ETCHER_ID);
    private static final int TEXTURE_WIDTH = 113;
    private static final int TEXTURE_HEIGHT = 62;
    private static final int TEXTURE_OFFSET_U = 32;
    private static final int TEXTURE_OFFSET_V = 12;
    private static final int PROGRESS_BAR_OFFSET_U = 52;
    private static final int PROGRESS_BAR_OFFSET_V = 24;
    private final IDrawableAnimated progressBar;

    public EtcherCategory(IGuiHelper guiHelper) {
        super(guiHelper, ETCHER_ID);
        IDrawableStatic progressBarTexture = guiHelper
            .drawableBuilder(texture, 178, 0, EtcherScreen.PROGRESS_BAR_WIDTH, EtcherScreen.PROGRESS_BAR_HEIGHT)
            .setTextureSize(EtcherScreen.ATLAS_WIDTH, ATLAS_HEIGHT)
            .build();
        progressBar =
            guiHelper.createAnimatedDrawable(progressBarTexture, 60, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends EtcherRecipe> getRecipeClass() {
        return EtcherRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EtcherRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        // output slot
        initSlot(itemStackGroup, 1, false, 88, 23);
        // input slots
        initSlot(itemStackGroup, 2, true, 5, 5);
        initSlot(itemStackGroup, 3, true, 28, 23);
        initSlot(itemStackGroup, 4, true, 5, 41);
        // set ingredients defined in setIngredients()
        itemStackGroup.set(ingredients);
    }

    @Override
    public void draw(EtcherRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        // draw animated progress bar
        progressBar.draw(matrixStack, PROGRESS_BAR_OFFSET_U, PROGRESS_BAR_OFFSET_V);
    }

    @Override
    protected int getAtlasWidth() {
        return EtcherScreen.ATLAS_WIDTH;
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
    protected IItemProvider getIconProvider() {
        return ModBlocks.ETCHER.get();
    }
}
