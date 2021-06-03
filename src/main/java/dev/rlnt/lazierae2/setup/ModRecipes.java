package dev.rlnt.lazierae2.setup;

import static dev.rlnt.lazierae2.Constants.*;

import dev.rlnt.lazierae2.recipe.type.AggregatorRecipe;
import dev.rlnt.lazierae2.recipe.type.CentrifugeRecipe;
import dev.rlnt.lazierae2.recipe.type.EnergizerRecipe;
import dev.rlnt.lazierae2.recipe.type.EtcherRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {

    private static final String UTILITY_CLASS = "Utility class";

    private ModRecipes() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    // recipe serializers
    public static final class Serializers {

        // registry
        @SuppressWarnings("java:S1700")
        static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = Registration.createRegistry(
            ForgeRegistries.RECIPE_SERIALIZERS
        );

        // fluix aggregator
        public static final RegistryObject<IRecipeSerializer<?>> AGGREGATOR = SERIALIZERS.register(
            AGGREGATOR_ID,
            AggregatorRecipe.Serializer::new
        );
        // pulse centrifuge
        public static final RegistryObject<IRecipeSerializer<?>> CENTRIFUGE = SERIALIZERS.register(
            CENTRIFUGE_ID,
            CentrifugeRecipe.Serializer::new
        );
        // crystal energizer
        public static final RegistryObject<IRecipeSerializer<?>> ENERGIZER = SERIALIZERS.register(
            ENERGIZER_ID,
            EnergizerRecipe.Serializer::new
        );
        // circuit etcher
        public static final RegistryObject<IRecipeSerializer<?>> ETCHER = SERIALIZERS.register(
            ETCHER_ID,
            EtcherRecipe.Serializer::new
        );

        private Serializers() {
            throw new IllegalStateException(ModRecipes.UTILITY_CLASS);
        }
    }

    public static final class Types {

        // fluix aggregator
        public static final IRecipeType<AggregatorRecipe> AGGREGATOR = IRecipeType.register(
            MOD_ID + ":" + AGGREGATOR_ID
        );
        // pulse centrifuge
        public static final IRecipeType<CentrifugeRecipe> CENTRIFUGE = IRecipeType.register(
            MOD_ID + ":" + CENTRIFUGE_ID
        );
        // crystal energizer
        public static final IRecipeType<EnergizerRecipe> ENERGIZER = IRecipeType.register(MOD_ID + ":" + ENERGIZER_ID);
        // circuit etcher
        public static final IRecipeType<EtcherRecipe> ETCHER = IRecipeType.register(MOD_ID + ":" + ETCHER_ID);

        private Types() {
            throw new IllegalStateException(ModRecipes.UTILITY_CLASS);
        }
    }
}
