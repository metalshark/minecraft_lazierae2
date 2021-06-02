package dev.rlnt.lazierae2.recipe.builder.base;

import static dev.rlnt.lazierae2.Constants.INPUT;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

public abstract class FinishedSingleRecipe<B extends SingleRecipeBuilder> extends AbstractFinishedRecipe<B> {

    protected FinishedSingleRecipe(ResourceLocation recipeID, B builder, String type) {
        super(recipeID, builder, type);
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        super.serializeRecipeData(json);
        json.add(INPUT, builder.input.toJson());
    }
}
