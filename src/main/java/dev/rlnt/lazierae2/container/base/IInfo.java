package dev.rlnt.lazierae2.container.base;

import dev.rlnt.lazierae2.util.TypeEnums;
import java.util.EnumMap;

public interface IInfo {
    /**
     * Gets the currently stored energy amount of the energy buffer.
     * @return currently stored energy amount
     */
    int getEnergyStored();

    /**
     * Gets the capacity of the energy buffer.
     * @return energy buffer capacity
     */
    int getEnergyCapacity();

    /**
     * Gets the current additional energy buffer capacity.
     * @return the additional energy buffer capacity
     */
    int getEnergyCapacityAdditional();

    /**
     * Gets the actual current energy consumption which already
     * has the current upgrade drain calculated.
     * @return current effective energy consumption
     */
    int getEffectiveEnergyConsumption();

    /**
     * Gets the current multiplier of the energy consumption.
     * @return the multiplier of the energy consumption
     */
    float getEnergyConsumptionMultiplier();

    /**
     * Gets the current progress on the currently processed recipe.
     * @return current processing progress
     */
    float getProgress();

    /**
     * Returns if the processor is currently processing a recipe.
     * @return true if the processor is currently processing, false otherwise
     */
    boolean isProcessing();

    /**
     * Gets the required processing time for the currently processed recipe.
     * This already has the current upgrade speed calculated.
     * @return the required processing time for the current recipe
     */
    int getEffectiveProcessTime();

    /**
     * Gets the current multiplier of the processing speed.
     * @return the multiplier of the processing speed
     */
    double getProcessTimeMultiplier(int upgradeAmount);

    /**
     * Gets the side configuration of the tile entity on the server
     * and converts it to a side configuration enum map.
     * @return the map of the current side configuration
     */
    EnumMap<TypeEnums.IO_SIDE, TypeEnums.IO_SETTING> getSideConfig();

    /**
     * Handles the info synchronization between the server-side tile entity
     * and the container to provide client-side information for the screen.
     */
    void syncInfo();
}
