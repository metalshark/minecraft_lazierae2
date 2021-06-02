package dev.rlnt.lazierae2.tile;

import static dev.rlnt.lazierae2.Constants.ETCHER_ID;

import dev.rlnt.lazierae2.container.EtcherContainer;
import dev.rlnt.lazierae2.inventory.EtcherItemHandler;
import dev.rlnt.lazierae2.inventory.base.MultiItemHandler;
import dev.rlnt.lazierae2.recipe.type.EtcherRecipe;
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

public class EtcherTile extends ProcessorTile<MultiItemHandler, EtcherRecipe> {

    private static final int SLOT_AMOUNT = 5;
    private static final int[] SLOT_INPUTS = { 2, 3, 4 };

    public EtcherTile() {
        super(ModTiles.ETCHER.get(), ETCHER_ID, SLOT_INPUTS);
        itemHandler = new EtcherItemHandler(SLOT_AMOUNT, this);
    }

    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory inventory, PlayerEntity player) {
        return new EtcherContainer(windowID, inventory, this, info);
    }

    @Override
    protected IItemProvider getItemProvider() {
        return ModBlocks.ETCHER.get();
    }

    @Override
    protected IRecipeType<EtcherRecipe> getRecipeType() {
        return ModRecipes.Types.ETCHER;
    }

    @Override
    protected int getEffectiveEnergyCapacity() {
        int baseBuffer = ModConfig.PROCESSING.etcherEnergyBuffer.get();
        double upgradeBuffer = ModConfig.PROCESSING.etcherEnergyBufferUpgrade.get();
        return getUpgradedValue(baseBuffer, upgradeBuffer, TypeEnums.OPERATION_TYPE.ADD);
    }

    @Override
    public int getEffectiveEnergy() {
        int baseEnergy = ModConfig.PROCESSING.etcherEnergyCostBase.get();
        double upgradeEnergy = ModConfig.PROCESSING.etcherEnergyCostUpgrade.get();
        return getUpgradedValue(baseEnergy, upgradeEnergy, TypeEnums.OPERATION_TYPE.MULTI);
    }

    @Override
    public int getEffectiveProcessTime(int baseProcessingTime) {
        double upgradProcessingTime = ModConfig.PROCESSING.etcherWorkTicksUpgrade.get();
        return getUpgradedValue(baseProcessingTime, upgradProcessingTime, TypeEnums.OPERATION_TYPE.DIV);
    }

    @Override
    public int getContainerSize() {
        return SLOT_AMOUNT;
    }
}
