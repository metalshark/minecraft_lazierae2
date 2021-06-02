package dev.rlnt.lazierae2.data.server;

import appeng.api.definitions.IBlocks;
import appeng.api.definitions.IMaterials;
import appeng.core.Api;
import dev.rlnt.lazierae2.recipe.builder.AggregatorRecipeBuilder;
import dev.rlnt.lazierae2.recipe.builder.CentrifugeRecipeBuilder;
import dev.rlnt.lazierae2.recipe.builder.EnergizerRecipeBuilder;
import dev.rlnt.lazierae2.recipe.builder.EtcherRecipeBuilder;
import dev.rlnt.lazierae2.setup.ModItems;
import dev.rlnt.lazierae2.setup.ModTags;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraftforge.common.Tags;

public class ModRecipes extends RecipeProvider {

    private final IBlocks aeBlocks = Api.instance().definitions().blocks();
    private final IMaterials aeMaterials = Api.instance().definitions().materials();

    public ModRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        fluixAggregatorRecipes(consumer);
        pulseCentrifugeRecipes(consumer);
        crystalEnergizerRecipes(consumer);
        circuitEtcherRecipies(consumer);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Lazier AE2 - Recipes";
    }

    private void fluixAggregatorRecipes(Consumer<IFinishedRecipe> consumer) {
        AggregatorRecipeBuilder
            .builder(ModItems.FLUIX_STEEL.get(), 1)
            .input(ModTags.Items.DUSTS_COAL)
            .input(aeMaterials.fluixDust())
            .input(Tags.Items.INGOTS_IRON)
            .processingTime(80)
            .build(consumer);
        AggregatorRecipeBuilder
            .builder(ModItems.CARB_FLUIX_DUST.get(), 1)
            .input(ModTags.Items.DUSTS_COAL)
            .input(aeMaterials.fluixDust())
            .input(aeMaterials.silicon())
            .processingTime(80)
            .build(consumer);
        AggregatorRecipeBuilder
            .builder(aeMaterials.fluixCrystal(), 2)
            .input(Tags.Items.GEMS_QUARTZ)
            .input(Tags.Items.DUSTS_REDSTONE)
            .input(aeMaterials.certusQuartzCrystalCharged())
            .processingTime(80)
            .build(consumer);
        AggregatorRecipeBuilder
            .builder(ModItems.RESONATING_GEM.get(), 1)
            .input(aeMaterials.skyDust())
            .input(Tags.Items.GEMS_DIAMOND)
            .input(aeMaterials.enderDust())
            .processingTime(80)
            .build(consumer);
        AggregatorRecipeBuilder
            .builder(ModItems.SPEC_CORE_1.get(), 1)
            .input(aeMaterials.skyDust())
            .input(aeMaterials.matterBall())
            .input(ModItems.CARB_FLUIX_DUST.get())
            .input(ModTags.Items.DUSTS_CARBONIC_FLUIX)
            .processingTime(80)
            .build(consumer);
    }

    private void pulseCentrifugeRecipes(Consumer<IFinishedRecipe> consumer) {
        CentrifugeRecipeBuilder
            .builder(aeMaterials.purifiedCertusQuartzCrystal(), 2)
            .input(aeMaterials.certusQuartzCrystal())
            .processingTime(160)
            .build(consumer);
        CentrifugeRecipeBuilder
            .builder(aeMaterials.purifiedNetherQuartzCrystal(), 2)
            .input(Tags.Items.GEMS_QUARTZ)
            .processingTime(160)
            .build(consumer);
        CentrifugeRecipeBuilder
            .builder(aeMaterials.purifiedFluixCrystal(), 2)
            .input(aeMaterials.fluixCrystal())
            .processingTime(160)
            .build(consumer);
        CentrifugeRecipeBuilder
            .builder(aeMaterials.skyDust(), 1)
            .input(aeBlocks.skyStoneBlock())
            .processingTime(160)
            .build(consumer);
        CentrifugeRecipeBuilder
            .builder(aeMaterials.enderDust(), 1)
            .input(Tags.Items.ENDER_PEARLS)
            .processingTime(160)
            .build(consumer);
        CentrifugeRecipeBuilder
            .builder(aeMaterials.flour(), 1)
            .input(Tags.Items.CROPS_WHEAT)
            .processingTime(160)
            .build(consumer);
    }

    private void crystalEnergizerRecipes(Consumer<IFinishedRecipe> consumer) {
        EnergizerRecipeBuilder
            .builder(aeMaterials.certusQuartzCrystalCharged(), 1)
            .input(aeMaterials.certusQuartzCrystal())
            .processingTime(100)
            .build(consumer);
    }

    private void circuitEtcherRecipies(Consumer<IFinishedRecipe> consumer) {
        EtcherRecipeBuilder
            .builder(aeMaterials.logicProcessor(), 1)
            .input(Tags.Items.INGOTS_GOLD)
            .input(Tags.Items.DUSTS_REDSTONE)
            .input(aeMaterials.silicon())
            .processingTime(120)
            .build(consumer);
        EtcherRecipeBuilder
            .builder(aeMaterials.calcProcessor(), 1)
            .input(aeMaterials.purifiedCertusQuartzCrystal())
            .input(Tags.Items.DUSTS_REDSTONE)
            .input(aeMaterials.silicon())
            .processingTime(120)
            .build(consumer);
        EtcherRecipeBuilder
            .builder(aeMaterials.engProcessor(), 1)
            .input(Tags.Items.GEMS_DIAMOND)
            .input(Tags.Items.DUSTS_REDSTONE)
            .input(aeMaterials.silicon())
            .processingTime(120)
            .build(consumer);
        EtcherRecipeBuilder
            .builder(ModItems.PARALLEL_PROCESSOR.get(), 1)
            .input(ModTags.Items.GEMS_RESONATING)
            .input(Tags.Items.DUSTS_REDSTONE)
            .input(aeMaterials.silicon())
            .processingTime(120)
            .build(consumer);
        EtcherRecipeBuilder
            .builder(ModItems.SPEC_PROCESSOR.get(), 1)
            .input(ModItems.SPEC_CORE_64.get())
            .input(Tags.Items.DUSTS_REDSTONE)
            .input(aeMaterials.silicon())
            .processingTime(120)
            .build(consumer);
        EtcherRecipeBuilder
            .builder(ModItems.FLUIX_IRON.get(), 1)
            .input(ModTags.Items.DUSTS_CARBONIC_FLUIX)
            .input(Tags.Items.INGOTS_IRON)
            .input(aeMaterials.skyDust().item())
            .processingTime(120)
            .build(consumer);
    }
}
