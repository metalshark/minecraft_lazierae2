package dev.rlnt.lazierae2.tile;

import static dev.rlnt.lazierae2.Constants.ENERGIZER_ID;

import dev.rlnt.lazierae2.container.EnergizerContainer;
import dev.rlnt.lazierae2.inventory.EnergizerItemHandler;
import dev.rlnt.lazierae2.inventory.base.SingleItemHandler;
import dev.rlnt.lazierae2.recipe.type.EnergizerRecipe;
import dev.rlnt.lazierae2.setup.ModBlocks;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModRecipes;
import dev.rlnt.lazierae2.setup.ModTiles;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import dev.rlnt.lazierae2.util.TypeEnums;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IItemProvider;

public class EnergizerTile extends ProcessorTile<SingleItemHandler, EnergizerRecipe> {

    private static final int SLOT_AMOUNT = 3;
    private static final int[] SLOT_INPUTS = { 2 };

    public EnergizerTile() {
        super(ModTiles.ENERGIZER.get(), ENERGIZER_ID, SLOT_INPUTS);
        itemHandler = new EnergizerItemHandler(SLOT_AMOUNT, this);
    }

    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory inventory, PlayerEntity player) {
        return new EnergizerContainer(windowID, inventory, this, info);
    }

    @Override
    protected IItemProvider getItemProvider() {
        return ModBlocks.ENERGIZER.get();
    }

    @Override
    protected IRecipeType<EnergizerRecipe> getRecipeType() {
        return ModRecipes.Types.ENERGIZER;
    }

    @Override
    protected int getEffectiveEnergyCapacity() {
        int baseBuffer = ModConfig.PROCESSING.energizerEnergyBuffer.get();
        double upgradeBuffer = ModConfig.PROCESSING.energizerEnergyBufferUpgrade.get();
        return getUpgradedValue(baseBuffer, upgradeBuffer, TypeEnums.OPERATION_TYPE.ADD);
    }

    @Override
    public int getEffectiveEnergy() {
        int baseEnergy = ModConfig.PROCESSING.energizerEnergyCostBase.get();
        double upgradeEnergy = ModConfig.PROCESSING.energizerEnergyCostUpgrade.get();
        return getUpgradedValue(baseEnergy, upgradeEnergy, TypeEnums.OPERATION_TYPE.MULTI);
    }

    @Override
    public int getEffectiveProcessTime(int baseProcessingTime) {
        double upgradProcessingTime = ModConfig.PROCESSING.energizerWorkTicksUpgrade.get();
        return getUpgradedValue(baseProcessingTime, upgradProcessingTime, TypeEnums.OPERATION_TYPE.DIV);
    }

    @Override
    public int getContainerSize() {
        return SLOT_AMOUNT;
    }
}
