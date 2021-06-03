package dev.rlnt.lazierae2.integration.jei.category.base;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.recipe.type.base.AbstractRecipe;
import dev.rlnt.lazierae2.recipe.type.base.MultiRecipe;
import dev.rlnt.lazierae2.recipe.type.base.SingleRecipe;
import dev.rlnt.lazierae2.util.TextUtil;
import dev.rlnt.lazierae2.util.TypeEnums.TRANSLATE_TYPE;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public abstract class ModRecipeCategory<R extends AbstractRecipe> implements IRecipeCategory<R> {

    protected static final int ATLAS_HEIGHT = 166;
    protected final ResourceLocation texture;
    private final String localizedName;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated progressBar;

    protected ModRecipeCategory(IGuiHelper guiHelper, String id) {
        texture = new ResourceLocation(MOD_ID, "textures/gui/" + id + ".png");
        localizedName = I18n.get(TextUtil.translate(TRANSLATE_TYPE.JEI, id).getKey());
        background =
            guiHelper
                .drawableBuilder(
                    texture,
                    getTextureOffsetU(),
                    getTextureOffsetV(),
                    getTextureWidth(),
                    getTextureHeight()
                )
                .setTextureSize(getAtlasWidth(), ATLAS_HEIGHT)
                .build();
        icon = guiHelper.createDrawableIngredient(new ItemStack(getIconProvider()));
        IDrawableStatic progressBarTexture = guiHelper
            .drawableBuilder(texture, 178, 0, getProgressBarWidth(), getProgressBarHeight())
            .setTextureSize(getAtlasWidth(), ATLAS_HEIGHT)
            .build();
        progressBar =
            guiHelper.createAnimatedDrawable(progressBarTexture, 60, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(R recipe, IIngredients ingredients) {
        // set output for the recipe
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
        // set input for the recipe
        if (recipe instanceof SingleRecipe) {
            // 1 input
            ingredients.setInput(VanillaTypes.ITEM, ((SingleRecipe) recipe).getInput().getItems()[0]);
        } else if (recipe instanceof MultiRecipe) {
            // 3 inputs
            List<ItemStack> inputs = new ArrayList<>();
            for (Ingredient input : ((MultiRecipe) recipe).getInputs()) {
                inputs.add(input.getItems()[0]);
            }
            ingredients.setInputs(VanillaTypes.ITEM, inputs);
        }
    }

    @Override
    public void draw(R recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        // draw animated progress bar
        progressBar.draw(matrixStack, getProgressBarOffsetU(), getProgressBarOffsetV());
    }

    /**
     * Inits an item slot for the JEI category.
     * Since JEI calculates the position by assuming slots are 18x18 pixels
     * for legacy reasons, we need to subtract 1 pixel.
     */
    protected void initSlot(IGuiItemStackGroup itemStackGroup, int slotIndex, boolean input, int xPos, int yPos) {
        itemStackGroup.init(slotIndex, input, xPos - 1, yPos - 1);
    }

    protected abstract int getAtlasWidth();

    protected abstract int getTextureWidth();

    protected abstract int getTextureHeight();

    protected abstract int getTextureOffsetU();

    protected abstract int getTextureOffsetV();

    protected abstract int getProgressBarWidth();

    protected abstract int getProgressBarHeight();

    protected abstract int getProgressBarOffsetU();

    protected abstract int getProgressBarOffsetV();

    protected abstract IItemProvider getIconProvider();
}
