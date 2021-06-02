package dev.rlnt.lazierae2.inventory.base;

import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import net.minecraft.item.ItemStack;

public abstract class SingleItemHandler extends AbstractItemHandler {

    protected SingleItemHandler(int size, ProcessorTile<?, ?> tile) {
        super(size, tile);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (slot == tile.getInputSlots()[0]) return isInput(stack);
        return super.isItemValid(slot, stack);
    }

    @Override
    public int getValidSlot(ItemStack stack) {
        // not used so just return 0
        return 0;
    }

    protected abstract boolean isInput(ItemStack stack);
}
