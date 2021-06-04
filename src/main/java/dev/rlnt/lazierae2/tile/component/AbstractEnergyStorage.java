package dev.rlnt.lazierae2.tile.component;

import static dev.rlnt.lazierae2.Constants.ENERGY;

import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class AbstractEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

    private final ProcessorTile<?, ?> tile;

    public AbstractEnergyStorage(ProcessorTile<?, ?> tile, int capacity) {
        super(capacity);
        this.tile = tile;
    }

    /**
     * Sets the current stored energy directly.
     * @param energy the new energy amount stored
     */
    public void setEnergy(int energy) {
        this.energy = energy;
        tile.setChanged();
    }

    /**
     * Sets the energy capacity direclty.
     * Also adjusts other necessary values which
     * depend on the capacity.
     * @param capacity the new capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
        maxExtract = capacity;
        maxReceive = capacity;
        if (energy > capacity) energy = capacity;
        tile.setChanged();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(ENERGY, getEnergyStored());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt(ENERGY));
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        // override this method to make sure the tile is saved
        if (!simulate) tile.setChanged();
        return super.receiveEnergy(maxReceive, simulate);
    }
}
