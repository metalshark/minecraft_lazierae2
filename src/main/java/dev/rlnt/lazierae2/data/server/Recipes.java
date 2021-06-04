package dev.rlnt.lazierae2.data.server;

import appeng.api.definitions.IBlocks;
import appeng.api.definitions.IMaterials;
import appeng.api.definitions.IParts;
import appeng.api.util.AEColor;
import appeng.core.Api;
import dev.rlnt.lazierae2.setup.ModBlocks;
import dev.rlnt.lazierae2.setup.ModItems;
import dev.rlnt.lazierae2.setup.ModTags;
import java.util.function.Consumer;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        IBlocks aeBlocks = Api.instance().definitions().blocks();
        IMaterials aeMaterials = Api.instance().definitions().materials();
        IParts aeParts = Api.instance().definitions().parts();

        final String hasCondition = "has_item";

        //region shaped-crafting
        // fluix aggregator
        ShapedRecipeBuilder
            .shaped(ModBlocks.AGGREGATOR.get())
            .pattern("fmf")
            .pattern("rur")
            .pattern("lcl")
            .define('f', aeMaterials.fluixPearl().item())
            .define('m', aeBlocks.molecularAssembler().item())
            .define('r', Tags.Items.DUSTS_REDSTONE)
            .define('u', ModItems.LOGIC_UNIT.get())
            .define('l', aeMaterials.logicProcessor().item())
            .define('c', aeBlocks.condenser().item())
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // pulse centrifuge
        ShapedRecipeBuilder
            .shaped(ModBlocks.CENTRIFUGE.get())
            .pattern("fsf")
            .pattern("pup")
            .pattern("fcf")
            .define('f', ModTags.Items.INGOTS_FLUIX_STEEL)
            .define('s', ModTags.Items.PROCESSOR_SPEC)
            .define('p', ModTags.Items.PROCESSOR_PARALLEL)
            .define('u', ModItems.LOGIC_UNIT.get())
            .define('c', ModItems.GROWTH_CHAMBER.get())
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // crystal energizer
        ShapedRecipeBuilder
            .shaped(ModBlocks.ENERGIZER.get())
            .pattern("fcf")
            .pattern("quq")
            .pattern("fdf")
            .define('f', ModTags.Items.INGOTS_FLUIX_STEEL)
            .define('c', aeBlocks.charger().item())
            .define('q', aeParts.quartzFiber().item())
            .define('u', ModItems.LOGIC_UNIT.get())
            .define('d', aeBlocks.energyCellDense().item())
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // circuit etcher
        ShapedRecipeBuilder
            .shaped(ModBlocks.ETCHER.get())
            .pattern("fif")
            .pattern("ili")
            .pattern("pup")
            .define('f', ModTags.Items.INGOTS_FLUIX_STEEL)
            .define('i', aeBlocks.inscriber().item())
            .define('l', ModItems.LOGIC_UNIT.get())
            .define('p', ModTags.Items.PROCESSOR_SPEC)
            .define('u', ModItems.UNIVERSAL_PRESS.get())
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // fluix logic unit
        ShapedRecipeBuilder
            .shaped(ModItems.LOGIC_UNIT.get())
            .pattern("sgs")
            .pattern("dpd")
            .pattern("sgs")
            .define('s', ModTags.Items.INGOTS_FLUIX_STEEL)
            .define('g', aeBlocks.quartzGlass().item())
            .define('d', ModTags.Items.DUSTS_CARBONIC_FLUIX)
            .define('p', aeMaterials.engProcessor().item())
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // growth chamber
        ShapedRecipeBuilder
            .shaped(ModItems.GROWTH_CHAMBER.get())
            .pattern("ama")
            .pattern("aba")
            .pattern("afa")
            .define('a', aeBlocks.quartzGrowthAccelerator().item())
            .define('m', aeBlocks.molecularAssembler().item())
            .define('b', Items.WATER_BUCKET.asItem())
            .define('f', aeParts.cableGlass().item(AEColor.TRANSPARENT).asItem())
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // universal press
        ShapedRecipeBuilder
            .shaped(ModItems.UNIVERSAL_PRESS.get())
            .pattern("ipi")
            .pattern("csl")
            .pattern("iei")
            .define('i', ModTags.Items.INGOTS_FLUIX_IRON)
            .define('p', aeMaterials.siliconPress().item())
            .define('c', aeMaterials.calcProcessorPress().item())
            .define('s', aeMaterials.singularity().item())
            .define('l', aeMaterials.logicProcessorPress().item())
            .define('e', aeMaterials.engProcessorPress().item())
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);
        //endregion shaped-crafting

        //region shapeless-crafting
        // carbonic fluix dust
        ShapelessRecipeBuilder
            .shapeless(ModItems.CARB_FLUIX_DUST.get())
            .requires(aeMaterials.fluixDust().item(), 2)
            .requires(ModTags.Items.DUSTS_COAL)
            .requires(ModTags.Items.DUSTS_COAL)
            .requires(aeMaterials.silicon())
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // speculation core x2
        ShapelessRecipeBuilder
            .shapeless(ModItems.SPEC_CORE_2.get())
            .requires(Tags.Items.DUSTS_REDSTONE)
            .requires(ModItems.SPEC_CORE_1.get(), 2)
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // speculation core x4
        ShapelessRecipeBuilder
            .shapeless(ModItems.SPEC_CORE_4.get())
            .requires(aeMaterials.silicon().item())
            .requires(ModItems.SPEC_CORE_2.get(), 2)
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // speculation core x8
        ShapelessRecipeBuilder
            .shapeless(ModItems.SPEC_CORE_8.get())
            .requires(aeMaterials.logicProcessor().item())
            .requires(ModItems.SPEC_CORE_4.get(), 2)
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // speculation core x16
        ShapelessRecipeBuilder
            .shapeless(ModItems.SPEC_CORE_16.get())
            .requires(aeMaterials.calcProcessor().item())
            .requires(ModItems.SPEC_CORE_8.get(), 2)
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // speculation core x32
        ShapelessRecipeBuilder
            .shapeless(ModItems.SPEC_CORE_32.get())
            .requires(aeMaterials.engProcessor().item())
            .requires(ModItems.SPEC_CORE_16.get(), 2)
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);

        // speculation core x64
        ShapelessRecipeBuilder
            .shapeless(ModItems.SPEC_CORE_64.get())
            .requires(ModItems.PARALLEL_PROCESSOR.get())
            .requires(ModItems.SPEC_CORE_32.get(), 2)
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);
        //endregion shapeless-crafting

        //region smelting
        // fluix steel ingot
        CookingRecipeBuilder
            .smelting(Ingredient.of(ModTags.Items.INGOTS_FLUIX_IRON), ModItems.FLUIX_STEEL.get(), 0.15F, 120)
            .unlockedBy(hasCondition, RecipeProvider.has(Items.COBBLESTONE))
            .save(consumer);
        //endregion smelting
    }
}
