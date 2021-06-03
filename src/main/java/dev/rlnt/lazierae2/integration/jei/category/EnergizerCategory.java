package dev.rlnt.lazierae2.integration.jei.category;

import static dev.rlnt.lazierae2.Constants.ENERGIZER_ID;
import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.integration.jei.category.base.ModRecipeCategory;
import dev.rlnt.lazierae2.recipe.type.EnergizerRecipe;
import dev.rlnt.lazierae2.screen.EnergizerScreen;
import dev.rlnt.lazierae2.setup.ModBlocks;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.util.TextUtil;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class EnergizerCategory extends ModRecipeCategory<EnergizerRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(MOD_ID, ENERGIZER_ID);
    private static final int TEXTURE_WIDTH = 90;
    private static final int TEXTURE_HEIGHT = 47;
    private static final int TEXTURE_OFFSET_U = 51;
    private static final int TEXTURE_OFFSET_V = 26;
    private static final int PROGRESS_BAR_OFFSET_U = 30;
    private static final int PROGRESS_BAR_OFFSET_V = 3;

    public EnergizerCategory(IGuiHelper guiHelper) {
        super(guiHelper, ENERGIZER_ID);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends EnergizerRecipe> getRecipeClass() {
        return EnergizerRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EnergizerRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        // output slot
        initSlot(itemStackGroup, 1, false, 65, 9);
        // input slot
        initSlot(itemStackGroup, 2, true, 5, 9);
        // set ingredients defined in setIngredients()
        itemStackGroup.set(ingredients);
    }

    @Override
    public void draw(EnergizerRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        // draw progress bar from super class
        super.draw(recipe, matrixStack, mouseX, mouseY);

        // draw required energy
        Minecraft
            .getInstance()
            .font.drawShadow(
                matrixStack,
                TextUtil.formatEnergy(
                    recipe.getProcessTime() * ModConfig.PROCESSING.energizerEnergyCostBase.get(),
                    false
                ),
                3,
                36,
                0xFFFFFFFF
            );
    }

    @Override
    protected int getAtlasWidth() {
        return EnergizerScreen.ATLAS_WIDTH;
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
        return EnergizerScreen.PROGRESS_BAR_WIDTH;
    }

    @Override
    protected int getProgressBarHeight() {
        return EnergizerScreen.PROGRESS_BAR_HEIGHT;
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
        return ModBlocks.ENERGIZER.get();
    }
}
