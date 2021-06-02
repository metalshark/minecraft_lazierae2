package dev.rlnt.lazierae2.tile;

import static dev.rlnt.lazierae2.Constants.CENTRIFUGE_ID;

import dev.rlnt.lazierae2.container.CentrifugeContainer;
import dev.rlnt.lazierae2.inventory.CentrifugeItemHandler;
import dev.rlnt.lazierae2.inventory.base.SingleItemHandler;
import dev.rlnt.lazierae2.recipe.type.CentrifugeRecipe;
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

public class CentrifugeTile extends ProcessorTile<SingleItemHandler, CentrifugeRecipe> {

    private static final int SLOT_AMOUNT = 3;
    private static final int[] SLOT_INPUTS = { 2 };

    public CentrifugeTile() {
        super(ModTiles.CENTRIFUGE.get(), CENTRIFUGE_ID, SLOT_INPUTS);
        itemHandler = new CentrifugeItemHandler(SLOT_AMOUNT, this);
    }

    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory inventory, PlayerEntity player) {
        return new CentrifugeContainer(windowID, inventory, this, info);
    }

    @Override
    protected IItemProvider getItemProvider() {
        return ModBlocks.CENTRIFUGE.get();
    }

    @Override
    protected IRecipeType<CentrifugeRecipe> getRecipeType() {
        return ModRecipes.Types.CENTRIFUGE;
    }

    @Override
    protected int getEffectiveEnergyCapacity() {
        int baseBuffer = ModConfig.PROCESSING.centrifugeEnergyBuffer.get();
        double upgradeBuffer = ModConfig.PROCESSING.centrifugeEnergyBufferUpgrade.get();
        return getUpgradedValue(baseBuffer, upgradeBuffer, TypeEnums.OPERATION_TYPE.ADD);
    }

    @Override
    public int getEffectiveEnergy() {
        int baseEnergy = ModConfig.PROCESSING.centrifugeEnergyCostBase.get();
        double upgradeEnergy = ModConfig.PROCESSING.centrifugeEnergyCostUpgrade.get();
        return getUpgradedValue(baseEnergy, upgradeEnergy, TypeEnums.OPERATION_TYPE.MULTI);
    }

    @Override
    public int getEffectiveProcessTime(int baseProcessingTime) {
        double upgradProcessingTime = ModConfig.PROCESSING.centrifugeWorkTicksUpgrade.get();
        return getUpgradedValue(baseProcessingTime, upgradProcessingTime, TypeEnums.OPERATION_TYPE.DIV);
    }

    @Override
    public int getContainerSize() {
        return SLOT_AMOUNT;
    }
}
