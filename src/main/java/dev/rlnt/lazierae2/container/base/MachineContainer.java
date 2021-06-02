package dev.rlnt.lazierae2.container.base;

import dev.rlnt.lazierae2.tile.base.MachineTile;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;

public abstract class MachineContainer<T extends MachineTile<?>> extends Container {

    protected final T tile;

    MachineContainer(@Nullable ContainerType<?> type, int windowID, PlayerInventory playerInventory, T tile) {
        super(type, windowID);
        this.tile = tile;

        initContainerInventory();
        initPlayerInventory(playerInventory);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return tile.isUsableByPlayer(playerIn);
    }

    /**
     * Creates the layout of the player inventory slots.
     * Should be called after the machine inventory has been created.
     * @param inventory the inventory the slots are created in
     */
    private void initPlayerInventory(PlayerInventory inventory) {
        // main inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // hotbar
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    public T getTile() {
        return tile;
    }

    /**
     * Creates the layout of the machine inventory slots.
     * Should be called before the machine inventory has been created.
     */
    protected abstract void initContainerInventory();
}
