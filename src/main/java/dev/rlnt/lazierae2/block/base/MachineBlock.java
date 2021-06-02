package dev.rlnt.lazierae2.block.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class MachineBlock extends HorizontalBlock {

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    MachineBlock(Properties builder) {
        super(builder);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    /**
     * Fired when the block is right-clicked by a player.
     */
    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType use(
        BlockState state,
        World world,
        BlockPos pos,
        PlayerEntity player,
        Hand hand,
        BlockRayTraceResult hit
    ) {
        // don't do shit on clientside or if player is shifting
        if (world.isClientSide() || player.isShiftKeyDown()) return ActionResultType.SUCCESS;

        // open the gui for the player who right-clicked the block
        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof INamedContainerProvider && player instanceof ServerPlayerEntity) {
            NetworkHooks.openGui(((ServerPlayerEntity) player), ((INamedContainerProvider) tile), pos);
        }
        return ActionResultType.CONSUME;
    }
}
