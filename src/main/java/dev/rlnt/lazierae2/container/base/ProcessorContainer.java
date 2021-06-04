package dev.rlnt.lazierae2.container.base;

import dev.rlnt.lazierae2.inventory.OutputSlot;
import dev.rlnt.lazierae2.inventory.UpgradeSlot;
import dev.rlnt.lazierae2.inventory.base.AbstractItemHandler;
import dev.rlnt.lazierae2.inventory.base.SingleItemHandler;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import dev.rlnt.lazierae2.util.GameUtil;
import dev.rlnt.lazierae2.util.IOUtil;
import dev.rlnt.lazierae2.util.TypeEnums;
import java.util.EnumMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.SlotItemHandler;

public abstract class ProcessorContainer<T extends ProcessorTile<?, ?>> extends MachineContainer<T> implements IInfo {

    private static final int PLAYER_INV_SIZE = 36;
    private final IIntArray info;

    protected ProcessorContainer(
        @Nullable ContainerType<?> type,
        int windowID,
        PlayerInventory playerInventory,
        T tile,
        IIntArray info
    ) {
        super(type, windowID, playerInventory, tile);
        this.info = info;
        syncInfo();
    }

    @Override
    protected void initContainerInventory() {
        AbstractItemHandler itemHandler = tile.getItemHandler();

        // upgrade slot
        addSlot(new UpgradeSlot(itemHandler, ProcessorTile.SLOT_UPGRADE, 146, 62, tile));

        if (itemHandler instanceof SingleItemHandler) {
            // 1 input slot processors
            // output slot
            addSlot(new OutputSlot(itemHandler, ProcessorTile.SLOT_OUTPUT, 116, 35));
            // input slot
            addSlot(new SlotItemHandler(itemHandler, tile.getInputSlots()[0], 56, 35));
        } else {
            // 3 input slot processors
            // output slot
            addSlot(new OutputSlot(itemHandler, ProcessorTile.SLOT_OUTPUT, 120, 35));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        // check if the slot exists and has an item inside
        if (slot == null || !slot.hasItem()) return stack;

        ItemStack slotStack = slot.getItem();
        stack = slotStack.copy();

        // decide where to put the item
        if (tile.isWithinProcessorSlots(index)) {
            if (!tryMergeToPlayerInventory(slotStack)) return ItemStack.EMPTY;
        } else if (isValidForInput(slotStack)) {
            if (!tryMergeToInput(slotStack)) return ItemStack.EMPTY;
        } else if (GameUtil.isUpgrade(slotStack)) {
            // check if the upgrade can be merged to the upgrade slot and merge
            int mergeableUpgrades = getMergeableUpgrades();
            if (mergeableUpgrades > 0) {
                tryMergeToUpgrade(slotStack, mergeableUpgrades);
            } else {
                return ItemStack.EMPTY;
            }
        }

        // check if something changed
        if (slotStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            // call this so the tile entity is marked as changed and saved
            slot.setChanged();
        }

        if (slotStack.getCount() == stack.getCount()) return ItemStack.EMPTY;
        slot.onTake(playerIn, slotStack);

        return stack;
    }

    /**
     * Checks if the passed in stack is valid for the input slots of the processor.
     * @param stack the ItemStack that is checked
     * @return true if the item is valid for the processor inputs, false otherwise
     */
    public boolean isValidForInput(ItemStack stack) {
        if (tile.getItemHandler() instanceof SingleItemHandler) {
            // 1 input slot
            return tile.getItemHandler().isItemValid(tile.getInputSlots()[0], stack);
        } else {
            // 3 input slots
            for (Integer slot : tile.getInputSlots()) {
                if (tile.getItemHandler().isItemValid(slot, stack)) return true;
            }
        }
        return false;
    }

    /**
     * Tries to merge the item to the input slots of the processor.
     * If there are 3 input slots, it also checks where the item has to go.
     * @param stack the ItemStack which should be merged
     * @return true if the stack was merged, false otherwise
     */
    private boolean tryMergeToInput(ItemStack stack) {
        if (tile.getItemHandler() instanceof SingleItemHandler) {
            // 1 input slot
            return moveItemStackTo(stack, tile.getInputSlots()[0], tile.getInputSlots()[0] + 1, false);
        } else {
            // 3 input slots
            int validSlot = tile.getItemHandler().getValidSlot(stack);
            if (validSlot == -1) return false;
            return moveItemStackTo(stack, validSlot, validSlot + 1, false);
        }
    }

    /**
     * Gets the amount of upgrades which fit into the upgrade slot
     * of the processor depending on how much are in it already.
     * @return the amount of fitting upgrades
     */
    private int getMergeableUpgrades() {
        AbstractItemHandler itemHandler = tile.getItemHandler();
        return (
            itemHandler.getSlotLimit(ProcessorTile.SLOT_UPGRADE) -
            itemHandler.getStackInSlot(ProcessorTile.SLOT_UPGRADE).getCount()
        );
    }

    /**
     * Tries to merge the processor upgrade to the upgrade slot.
     * Adjusts the clicked stacked so only the fitting amount is merged.
     * @param slotStack the ItemStack of processor upgrades
     * @param mergeableUpgrades the amount of upgrades which fit into the upgrade slot
     */
    private void tryMergeToUpgrade(ItemStack slotStack, int mergeableUpgrades) {
        if (slotStack.getCount() > mergeableUpgrades) {
            ItemStack splitStack = slotStack.copy();
            splitStack.setCount(mergeableUpgrades);
            slotStack.shrink(mergeableUpgrades);
            moveItemStackTo(splitStack, ProcessorTile.SLOT_UPGRADE, ProcessorTile.SLOT_UPGRADE + 1, false);
        } else {
            moveItemStackTo(slotStack, ProcessorTile.SLOT_UPGRADE, ProcessorTile.SLOT_UPGRADE + 1, false);
        }
    }

    /**
     * Tries to merge the item from the processor inventory to the player inventory.
     * @param stack the ItemStack which should be placed in the player inventory
     * @return true if the stack was merged, false otherwise
     */
    private boolean tryMergeToPlayerInventory(ItemStack stack) {
        return moveItemStackTo(stack, tile.getContainerSize(), tile.getContainerSize() + PLAYER_INV_SIZE, false);
    }

    @Override
    public int getEnergyStored() {
        int upper = info.get(1) & 0xFFFF;
        int lower = info.get(0) & 0xFFFF;
        return (upper << 16) + lower;
    }

    @Override
    public int getEnergyCapacity() {
        int upper = info.get(3) & 0xFFFF;
        int lower = info.get(2) & 0xFFFF;
        return (upper << 16) + lower;
    }

    @Override
    public int getEffectiveEnergyConsumption() {
        return info.get(4);
    }

    @Override
    public float getProgress() {
        return info.get(5);
    }

    @Override
    public boolean isProcessing() {
        return getProgress() > 0f;
    }

    @Override
    public int getEffectiveProcessTime() {
        return info.get(6);
    }

    @Override
    public EnumMap<TypeEnums.IO_SIDE, TypeEnums.IO_SETTING> getSideConfig() {
        return (EnumMap<TypeEnums.IO_SIDE, TypeEnums.IO_SETTING>) IOUtil.getSideConfigFromArray(
            new int[] { info.get(7), info.get(8), info.get(9), info.get(10), info.get(11), info.get(12) }
        );
    }

    @Override
    public boolean isAutoExtracting() {
        return info.get(13) == 1;
    }

    @Override
    public void syncInfo() {
        addDataSlots(info);
    }
}
