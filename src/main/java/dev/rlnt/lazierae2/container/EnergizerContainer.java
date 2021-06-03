package dev.rlnt.lazierae2.container;

import dev.rlnt.lazierae2.container.base.ProcessorContainer;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModContainers;
import dev.rlnt.lazierae2.tile.EnergizerTile;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class EnergizerContainer extends ProcessorContainer<EnergizerTile> {

    public EnergizerContainer(int windowID, PlayerInventory playerInventory, EnergizerTile tile, IIntArray info) {
        super(ModContainers.ENERGIZER.get(), windowID, playerInventory, tile, info);
    }

    public EnergizerContainer(int windowID, PlayerInventory playerInventory, EnergizerTile tile) {
        this(windowID, playerInventory, tile, new IntArray(ProcessorTile.INFO_SIZE));
    }

    @Override
    public int getEnergyCapacityAdditional() {
        return getEnergyCapacity() - ModConfig.PROCESSING.energizerEnergyBuffer.get();
    }

    @Override
    public float getEnergyConsumptionMultiplier() {
        return (float) getEffectiveEnergyConsumption() / (float) ModConfig.PROCESSING.energizerEnergyCostBase.get();
    }

    @Override
    public double getProcessTimeMultiplier(int upgradeAmount) {
        return Math.pow(ModConfig.PROCESSING.energizerWorkTicksUpgrade.get(), upgradeAmount);
    }
}
