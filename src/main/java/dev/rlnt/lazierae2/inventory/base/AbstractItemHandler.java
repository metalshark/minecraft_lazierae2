package dev.rlnt.lazierae2.inventory.base;

import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import dev.rlnt.lazierae2.util.GameUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public abstract class AbstractItemHandler extends ItemStackHandler {

    public final ProcessorTile<?, ?> tile;
    private final int size;

    AbstractItemHandler(int size, ProcessorTile<?, ?> tile) {
        super(size);
        this.tile = tile;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot == ProcessorTile.SLOT_UPGRADE) return ModConfig.PROCESSING.processorUpgradeSlots.get();
        return super.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (slot == ProcessorTile.SLOT_UPGRADE) return GameUtil.isUpgrade(stack);
        return super.isItemValid(slot, stack);
    }

    @Override
    protected void onContentsChanged(int slot) {
        tile.setChanged();
    }

    public abstract int getValidSlot(ItemStack stack);
}
