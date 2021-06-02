package dev.rlnt.lazierae2.inventory;

import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class UpgradeSlot extends SlotItemHandler {

    private final ProcessorTile<?, ?> tile;
    private int upgradeAmount;

    public UpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ProcessorTile<?, ?> tile) {
        super(itemHandler, index, xPosition, yPosition);
        this.tile = tile;
        upgradeAmount = tile.getUpgradeStack().isEmpty() ? 0 : tile.getUpgradeStack().getCount();
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ProcessorTile.isUpgrade(stack);
    }

    @Override
    public int getMaxStackSize() {
        return ModConfig.PROCESSING.processorUpgradeSlots.get();
    }

    @Override
    public void setChanged() {
        int newUpgradeAmount = getItem().getCount();
        if (newUpgradeAmount != upgradeAmount) {
            tile.refreshEnergyCapacity();
            upgradeAmount = newUpgradeAmount;
        }
        super.setChanged();
    }
}
