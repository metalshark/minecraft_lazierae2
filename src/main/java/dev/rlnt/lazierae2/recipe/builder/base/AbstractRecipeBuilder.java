package dev.rlnt.lazierae2.recipe.builder.base;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractRecipeBuilder {

    protected final ItemStack output;
    protected int processingTime = 0;
    protected String id;

    AbstractRecipeBuilder(IItemProvider output, int outputCount) {
        this.output = new ItemStack(output, outputCount);
        id = getId();
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        ResourceLocation outputID = output.getItem().getRegistryName();
        String modID = "minecraft".equals(Objects.requireNonNull(outputID).getNamespace())
            ? MOD_ID
            : outputID.getNamespace();
        ResourceLocation recipeID = new ResourceLocation(modID, id + "/" + outputID.getPath());
        checkProcessingTime();
        build(consumer, recipeID);
    }

    protected abstract void build(Consumer<IFinishedRecipe> consumer, ResourceLocation recipeID);

    protected abstract String getId();

    protected abstract void checkProcessingTime();
}
