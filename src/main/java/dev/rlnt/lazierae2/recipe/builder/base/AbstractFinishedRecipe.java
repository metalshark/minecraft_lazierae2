package dev.rlnt.lazierae2.recipe.builder.base;

import static dev.rlnt.lazierae2.Constants.*;

import com.google.gson.JsonObject;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractFinishedRecipe<B extends AbstractRecipeBuilder> implements IFinishedRecipe {

    protected final B builder;
    private final ResourceLocation recipeID;
    private final String type;

    AbstractFinishedRecipe(ResourceLocation recipeID, B builder, String type) {
        this.recipeID = recipeID;
        this.builder = builder;
        this.type = type;
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        json.addProperty(PROCESSING_TIME, builder.processingTime);

        JsonObject output = new JsonObject();
        output.addProperty(
            ITEM,
            Objects
                .requireNonNull(
                    builder.output.getItem().getRegistryName(),
                    "Output in " + type + "-recipe was not defined!"
                )
                .toString()
        );
        if (builder.output.getCount() > 1) output.addProperty(COUNT, builder.output.getCount());
        json.add(OUTPUT, output);
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return recipeID;
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
