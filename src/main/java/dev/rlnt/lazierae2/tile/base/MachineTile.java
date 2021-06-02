package dev.rlnt.lazierae2.tile.base;

import static dev.rlnt.lazierae2.Constants.INVENTORY;

import dev.rlnt.lazierae2.inventory.base.AbstractItemHandler;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class MachineTile<I extends AbstractItemHandler>
    extends TileEntity
    implements ITickableTileEntity, INamedContainerProvider, IInventory, ISidedInventory {

    protected I itemHandler;

    MachineTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public I getItemHandler() {
        return itemHandler;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        itemHandler.deserializeNBT(nbt.getCompound(INVENTORY));
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put(INVENTORY, itemHandler.serializeNBT());
        return super.save(nbt);
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getItem(int index) {
        return itemHandler.getStackInSlot(index);
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int index, int count) {
        return index >= 0 && index < itemHandler.getSlots() && !itemHandler.getStackInSlot(index).isEmpty() && count > 0
            ? itemHandler.getStackInSlot(index).split(count)
            : ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        if (index >= 0 && index < itemHandler.getSlots()) {
            ItemStack oldStack = itemHandler.getStackInSlot(index);
            itemHandler.setStackInSlot(index, ItemStack.EMPTY);
            return oldStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        itemHandler.setStackInSlot(index, stack);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return isUsableByPlayer(player);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    /**
     * Determines if the tile entity is currently usable by the
     * passed in player.
     * @param player the player to check
     * @return true if the player can use the TE, false otherwise
     */
    public boolean isUsableByPlayer(PlayerEntity player) {
        return (
            level != null &&
            level.getBlockEntity(worldPosition) == this &&
            player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <=
            64
        );
    }
}
