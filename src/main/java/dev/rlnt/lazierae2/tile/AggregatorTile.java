package dev.rlnt.lazierae2.tile;

import static dev.rlnt.lazierae2.Constants.AGGREGATOR_ID;

import dev.rlnt.lazierae2.container.AggregatorContainer;
import dev.rlnt.lazierae2.inventory.AggregatorItemHandler;
import dev.rlnt.lazierae2.inventory.base.MultiItemHandler;
import dev.rlnt.lazierae2.recipe.type.AggregatorRecipe;
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

public class AggregatorTile extends ProcessorTile<MultiItemHandler, AggregatorRecipe> {

    private static final int SLOT_AMOUNT = 5;
    private static final int[] SLOT_INPUTS = { 2, 3, 4 };

    public AggregatorTile() {
        super(ModTiles.AGGREGATOR.get(), AGGREGATOR_ID, SLOT_INPUTS);
        itemHandler = new AggregatorItemHandler(SLOT_AMOUNT, this);
    }

    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory inventory, PlayerEntity player) {
        return new AggregatorContainer(windowID, inventory, this, info);
    }

    @Override
    public int getContainerSize() {
        return SLOT_AMOUNT;
    }

    @Override
    protected IItemProvider getItemProvider() {
        return ModBlocks.AGGREGATOR.get();
    }

    @Override
    protected IRecipeType<AggregatorRecipe> getRecipeType() {
        return ModRecipes.Types.AGGREGATOR;
    }

    @Override
    protected int getEffectiveEnergyCapacity() {
        int baseBuffer = ModConfig.PROCESSING.aggregatorEnergyBuffer.get();
        double upgradeBuffer = ModConfig.PROCESSING.aggregatorEnergyBufferUpgrade.get();
        return getUpgradedValue(baseBuffer, upgradeBuffer, TypeEnums.OPERATION_TYPE.ADD);
    }

    @Override
    public int getEffectiveEnergy() {
        int baseEnergy = ModConfig.PROCESSING.aggregatorEnergyCostBase.get();
        double upgradeEnergy = ModConfig.PROCESSING.aggregatorEnergyCostUpgrade.get();
        return getUpgradedValue(baseEnergy, upgradeEnergy, TypeEnums.OPERATION_TYPE.MULTI);
    }

    @Override
    public int getEffectiveProcessTime(int baseProcessingTime) {
        double upgradProcessingTime = ModConfig.PROCESSING.aggregatorWorkTicksUpgrade.get();
        return getUpgradedValue(baseProcessingTime, upgradProcessingTime, TypeEnums.OPERATION_TYPE.DIV);
    }
}
