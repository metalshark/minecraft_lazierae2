package dev.rlnt.lazierae2.recipe.builder.base;

import static dev.rlnt.lazierae2.Constants.INPUT;
import static dev.rlnt.lazierae2.Constants.INPUTS;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public abstract class FinishedMultiRecipe<B extends MultiRecipeBuilder> extends AbstractFinishedRecipe<B> {

    protected FinishedMultiRecipe(ResourceLocation recipeID, B builder, String type) {
        super(recipeID, builder, type);
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        super.serializeRecipeData(json);

        JsonArray inputs = new JsonArray();
        builder.inputs.forEach(input -> inputs.add(serializeIngredient(input)));
        json.add(INPUTS, inputs);
    }

    private JsonElement serializeIngredient(Ingredient input) {
        JsonObject json = new JsonObject();
        json.add(INPUT, input.toJson());
        return json;
    }
}
