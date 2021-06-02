package dev.rlnt.lazierae2.integration.jei;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.container.AggregatorContainer;
import dev.rlnt.lazierae2.container.CentrifugeContainer;
import dev.rlnt.lazierae2.container.EnergizerContainer;
import dev.rlnt.lazierae2.container.EtcherContainer;
import dev.rlnt.lazierae2.integration.jei.category.AggregatorCategory;
import dev.rlnt.lazierae2.integration.jei.category.CentrifugeCategory;
import dev.rlnt.lazierae2.integration.jei.category.EnergizerCategory;
import dev.rlnt.lazierae2.integration.jei.category.EtcherCategory;
import dev.rlnt.lazierae2.screen.AggregatorScreen;
import dev.rlnt.lazierae2.screen.CentrifugeScreen;
import dev.rlnt.lazierae2.screen.EnergizerScreen;
import dev.rlnt.lazierae2.screen.EtcherScreen;
import dev.rlnt.lazierae2.setup.ModBlocks;
import dev.rlnt.lazierae2.setup.ModRecipes;
import dev.rlnt.lazierae2.util.GameUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("unused")
@JeiPlugin
public class ModJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new AggregatorCategory(guiHelper));
        registration.addRecipeCategories(new CentrifugeCategory(guiHelper));
        registration.addRecipeCategories(new EnergizerCategory(guiHelper));
        registration.addRecipeCategories(new EtcherCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = GameUtil.getRecipeManager(null);
        registration.addRecipes(recipeManager.getAllRecipesFor(ModRecipes.Types.AGGREGATOR), AggregatorCategory.UID);
        registration.addRecipes(recipeManager.getAllRecipesFor(ModRecipes.Types.CENTRIFUGE), CentrifugeCategory.UID);
        registration.addRecipes(recipeManager.getAllRecipesFor(ModRecipes.Types.ENERGIZER), EnergizerCategory.UID);
        registration.addRecipes(recipeManager.getAllRecipesFor(ModRecipes.Types.ETCHER), EtcherCategory.UID);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(AggregatorContainer.class, AggregatorCategory.UID, 2, 3, 5, 36);
        registration.addRecipeTransferHandler(CentrifugeContainer.class, CentrifugeCategory.UID, 2, 1, 3, 36);
        registration.addRecipeTransferHandler(EnergizerContainer.class, EnergizerCategory.UID, 2, 1, 3, 36);
        registration.addRecipeTransferHandler(EtcherContainer.class, EtcherCategory.UID, 2, 3, 5, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.AGGREGATOR.get()), AggregatorCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CENTRIFUGE.get()), CentrifugeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ENERGIZER.get()), EnergizerCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ETCHER.get()), EtcherCategory.UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AggregatorScreen.class, 84, 36, 24, 13, AggregatorCategory.UID);
        registration.addRecipeClickArea(CentrifugeScreen.class, 80, 36, 22, 13, CentrifugeCategory.UID);
        registration.addRecipeClickArea(EnergizerScreen.class, 81, 29, 22, 28, EnergizerCategory.UID);
        registration.addRecipeClickArea(EtcherScreen.class, 84, 36, 22, 13, EtcherCategory.UID);
    }
}
