package dev.rlnt.lazierae2.util;

import static dev.rlnt.lazierae2.Constants.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.rlnt.lazierae2.recipe.type.base.AbstractRecipe;
import dev.rlnt.lazierae2.recipe.type.base.MultiRecipe;
import dev.rlnt.lazierae2.recipe.type.base.SingleRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;

public class RecipeUtil {

    private RecipeUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Utility function to get an ingredient from
     * a given Json Element.
     * @param element the json element to get the ingredient from
     * @return the parsed deserialized ingredient
     */
    private static Ingredient deserializeIngredient(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject json = element.getAsJsonObject();
            if (json.has(INPUT)) return Ingredient.fromJson(json.get(INPUT));
        }
        return Ingredient.fromJson(element);
    }

    /**
     * Deserializes recipe information from a JSON.
     * @param json the json to read
     * @param recipe the recipe to apply the information to
     * @return the recipe with the deserialized information
     */
    public static AbstractRecipe fromJSON(JsonObject json, AbstractRecipe recipe) {
        recipe.setProcessTime(JSONUtils.getAsInt(json, PROCESSING_TIME, 200));
        recipe.setOutput(ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, OUTPUT)));

        if (recipe instanceof SingleRecipe) {
            ((SingleRecipe) recipe).setInput(Ingredient.fromJson(json.get(INPUT)));
        } else {
            JSONUtils
                .getAsJsonArray(json, INPUTS)
                .forEach(
                    element -> {
                        Ingredient input = deserializeIngredient(element);
                        ((MultiRecipe) recipe).inputs.add(input);
                    }
                );
        }

        return recipe;
    }

    /**
     * Deserializes recipe information from a packet buffer.
     * @param buffer the packet buffer to read
     * @param recipe the recipe to apply the information to
     * @return the recipe with the deserialized information
     */
    public static AbstractRecipe fromNetwork(PacketBuffer buffer, AbstractRecipe recipe) {
        recipe.setProcessTime(buffer.readInt());
        recipe.setOutput(buffer.readItem());

        if (recipe instanceof SingleRecipe) {
            ((SingleRecipe) recipe).setInput(Ingredient.fromNetwork(buffer));
        } else {
            ((MultiRecipe) recipe).inputs.clear();
            int inputAmount = buffer.readByte();
            for (int i = 0; i < inputAmount; i++) {
                ((MultiRecipe) recipe).inputs.add(Ingredient.fromNetwork(buffer));
            }
        }

        return recipe;
    }

    /**
     * Serializes recipe information to a packet buffer.
     * @param buffer the packet buffer to write to
     * @param recipe the recipe to get the information from
     */
    public static void toNetwork(PacketBuffer buffer, AbstractRecipe recipe) {
        buffer.writeInt(recipe.getProcessTime());
        buffer.writeItem(recipe.getOutput());

        if (recipe instanceof SingleRecipe) {
            ((SingleRecipe) recipe).getInput().toNetwork(buffer);
        } else {
            buffer.writeByte(((MultiRecipe) recipe).inputs.size());
            ((MultiRecipe) recipe).inputs.forEach(input -> input.toNetwork(buffer));
        }
    }
}
