package dev.rlnt.lazierae2.container;

import dev.rlnt.lazierae2.container.base.ProcessorContainer;
import dev.rlnt.lazierae2.inventory.base.MultiItemHandler;
import dev.rlnt.lazierae2.inventory.component.MultiInputSlot;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModContainers;
import dev.rlnt.lazierae2.tile.AggregatorTile;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class AggregatorContainer extends ProcessorContainer<AggregatorTile> {

    public AggregatorContainer(int windowID, PlayerInventory playerInventory, AggregatorTile tile, IIntArray info) {
        super(ModContainers.AGGREGATOR.get(), windowID, playerInventory, tile, info);
    }

    public AggregatorContainer(int windowID, PlayerInventory playerInventory, AggregatorTile tile) {
        this(windowID, playerInventory, tile, new IntArray(ProcessorTile.INFO_SIZE));
    }

    @Override
    protected void initContainerInventory() {
        // create upgrade and output slot first
        super.initContainerInventory();

        MultiItemHandler itemHandler = tile.getItemHandler();

        // input slots
        addSlot(new MultiInputSlot(itemHandler, tile.getInputSlots()[0], 40, 25, this));
        addSlot(new MultiInputSlot(itemHandler, tile.getInputSlots()[1], 60, 35, this));
        addSlot(new MultiInputSlot(itemHandler, tile.getInputSlots()[2], 40, 45, this));
    }

    @Override
    public int getEnergyCapacityAdditional() {
        return getEnergyCapacity() - ModConfig.PROCESSING.aggregatorEnergyBuffer.get();
    }

    @Override
    public float getEnergyConsumptionMultiplier() {
        return (float) getEffectiveEnergyConsumption() / (float) ModConfig.PROCESSING.aggregatorEnergyCostBase.get();
    }

    @Override
    public double getProcessTimeMultiplier(int upgradeAmount) {
        return Math.pow(ModConfig.PROCESSING.aggregatorWorkTicksUpgrade.get(), upgradeAmount);
    }
}
