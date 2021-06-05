package dev.rlnt.lazierae2.tile.base;

import dev.rlnt.lazierae2.util.TypeEnums;
import net.minecraft.util.IIntArray;

public interface IIntArrayIO extends IIntArray {
    /**
     * Returns the current io configuration for the passed in side.
     * @param side the side to get the io configuration from
     * @return the io configuration as int
     */
    int getIOSetting(TypeEnums.IO_SIDE side);
}
