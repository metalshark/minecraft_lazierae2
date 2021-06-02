package dev.rlnt.lazierae2.block;

import dev.rlnt.lazierae2.block.base.ProcessorBlock;
import dev.rlnt.lazierae2.setup.ModTiles;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class EnergizerBlock extends ProcessorBlock {

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTiles.ENERGIZER.get().create();
    }
}
