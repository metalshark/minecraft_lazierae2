package dev.rlnt.lazierae2.container;

import dev.rlnt.lazierae2.container.base.ProcessorContainer;
import dev.rlnt.lazierae2.inventory.OutputSlot;
import dev.rlnt.lazierae2.inventory.UpgradeSlot;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModContainers;
import dev.rlnt.lazierae2.tile.CentrifugeTile;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CentrifugeContainer extends ProcessorContainer<CentrifugeTile> {

    public CentrifugeContainer(int windowID, PlayerInventory playerInventory, CentrifugeTile tile, IIntArray info) {
        super(ModContainers.CENTRIFUGE.get(), windowID, playerInventory, tile, info);
    }

    public CentrifugeContainer(int windowID, PlayerInventory playerInventory, CentrifugeTile tile) {
        this(windowID, playerInventory, tile, new IntArray(ProcessorTile.INFO_SIZE));
    }

    @Override
    protected void initContainerInventory() {
        ItemStackHandler itemHandler = tile.getItemHandler();

        // upgrade slot
        addSlot(new UpgradeSlot(itemHandler, ProcessorTile.SLOT_UPGRADE, 146, 62, tile));
        // output slot
        addSlot(new OutputSlot(itemHandler, ProcessorTile.SLOT_OUTPUT, 116, 35));
        // input slot
        addSlot(new SlotItemHandler(itemHandler, tile.getInputSlots()[0], 56, 35));
    }

    @Override
    public int getEnergyCapacityAdditional() {
        return getEnergyCapacity() - ModConfig.PROCESSING.centrifugeEnergyBuffer.get();
    }

    @Override
    public float getEnergyConsumptionMultiplier() {
        return (float) getEffectiveEnergyConsumption() / (float) ModConfig.PROCESSING.centrifugeEnergyCostBase.get();
    }

    @Override
    public double getProcessTimeMultiplier(int upgradeAmount) {
        return Math.pow(ModConfig.PROCESSING.centrifugeWorkTicksUpgrade.get(), upgradeAmount);
    }
}
