package dev.rlnt.lazierae2.inventory.component;

import dev.rlnt.lazierae2.container.base.ProcessorContainer;
import dev.rlnt.lazierae2.inventory.base.MultiItemHandler;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class MultiInputSlot extends SlotItemHandler {

    private final MultiItemHandler tileItemHandler;
    private final ProcessorContainer<?> parent;

    public MultiInputSlot(
        MultiItemHandler itemHandler,
        int index,
        int xPosition,
        int yPosition,
        ProcessorContainer<?> container
    ) {
        super(itemHandler, index, xPosition, yPosition);
        tileItemHandler = itemHandler;
        parent = container;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        if (!parent.isValidForInput(stack)) return false;
        if (tileItemHandler.getValidSlot(stack) != getSlotIndex()) return false;
        return super.mayPlace(stack);
    }
}
