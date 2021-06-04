package dev.rlnt.lazierae2.block.base;

import static dev.rlnt.lazierae2.Constants.*;

import dev.rlnt.lazierae2.screen.base.ProcessorScreen;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import dev.rlnt.lazierae2.util.IOUtil;
import dev.rlnt.lazierae2.util.TextUtil;
import dev.rlnt.lazierae2.util.TypeEnums.TRANSLATE_TYPE;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.ArrayUtils;

public abstract class ProcessorBlock extends MachineBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    protected ProcessorBlock() {
        super(
            Properties
                .of(Material.METAL)
                .strength(5f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL)
                .lightLevel(state -> state.getValue(LIT).equals(true) ? 5 : 0)
        );
        registerDefaultState(defaultBlockState().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    /**
     * Fired when the block is placed.
     */
    @Override
    public void setPlacedBy(
        World world,
        BlockPos pos,
        BlockState state,
        @Nullable LivingEntity placer,
        ItemStack stack
    ) {
        // set the stored information from the item to the block
        CompoundNBT nbt = stack.getTag();
        if (nbt != null) {
            ProcessorTile<?, ?> tile = (ProcessorTile<?, ?>) world.getBlockEntity(pos);
            if (tile != null) {
                if (nbt.contains(UPGRADE)) tile
                    .getItemHandler()
                    .setStackInSlot(ProcessorTile.SLOT_UPGRADE, ItemStack.of(nbt.getCompound(UPGRADE)));
                tile.refreshEnergyCapacity();
                if (nbt.contains(ENERGY)) tile.getEnergyStorage().deserializeNBT(nbt.getCompound(ENERGY));
                if (nbt.contains(IO_CONFIG)) tile.setSideConfig(
                    IOUtil.getSideConfigFromArray(nbt.getIntArray(IO_CONFIG))
                );
                if (nbt.contains(AUTO_EXTRACT)) tile.toggleAutoExtract();
            }
        }

        super.setPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        @Nullable IBlockReader world,
        List<ITextComponent> tooltip,
        ITooltipFlag flagIn
    ) {
        // add a tooltip with the stored upgrades and energy for the processor items
        CompoundNBT nbt = stack.getTag();
        int upgrades = 0;
        int energy = 0;
        if (nbt != null) {
            if (nbt.contains(UPGRADE)) upgrades = ItemStack.of(nbt.getCompound(UPGRADE)).getCount();
            if (nbt.contains(ENERGY)) energy = nbt.getCompound(ENERGY).getInt(ENERGY);
        }

        if (Screen.hasShiftDown()) {
            // show the actual values when shift is pressed
            tooltip.add(
                TextUtil
                    .translate(TRANSLATE_TYPE.TOOLTIP, "item_upgrade", TextFormatting.YELLOW)
                    .append(TextUtil.colorize(String.format(" %s", upgrades), TextFormatting.WHITE))
            );
            tooltip.add(
                TextUtil
                    .translate(TRANSLATE_TYPE.TOOLTIP, "item_energy", TextFormatting.YELLOW)
                    .append(
                        TextUtil.colorize(
                            String.format(" %s", TextUtil.formatEnergy(energy, false)),
                            TextFormatting.WHITE
                        )
                    )
            );
        } else {
            // show tooltip that shift can be pressed to see stored values
            tooltip.add(ProcessorScreen.getDetailsTooltip());
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    /**
     * Fired when the block is removed.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        // don't do shit if the state didn't change or the tile is null
        ProcessorTile<?, ?> tile = (ProcessorTile<?, ?>) world.getBlockEntity(pos);
        if (tile != null && state.getBlock() != newState.getBlock()) {
            // drop processor inventory
            IItemHandler itemHandler = tile.getItemHandler();
            int[] inputSlots = tile.getInputSlots();
            int[] outputSlots = { ProcessorTile.SLOT_OUTPUT };
            int[] dropSlots = ArrayUtils.addAll(inputSlots, outputSlots);
            for (int slot : dropSlots) {
                ItemStack stack = itemHandler.getStackInSlot(slot);
                if (!stack.isEmpty()) world.addFreshEntity(
                    new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack)
                );
            }

            // drop block item with upgrade and energy information
            ItemStack blockStack = tile.getBlockItem();
            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), blockStack));
        }

        // call super last because it actually removes the tile entity
        super.onRemove(state, world, pos, newState, isMoving);
    }
}
