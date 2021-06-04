package dev.rlnt.lazierae2.tile.base;

import static dev.rlnt.lazierae2.Constants.*;

import dev.rlnt.lazierae2.block.base.ProcessorBlock;
import dev.rlnt.lazierae2.inventory.base.AbstractItemHandler;
import dev.rlnt.lazierae2.inventory.base.MultiItemHandler;
import dev.rlnt.lazierae2.inventory.base.SingleItemHandler;
import dev.rlnt.lazierae2.recipe.type.base.AbstractRecipe;
import dev.rlnt.lazierae2.tile.component.AbstractEnergyStorage;
import dev.rlnt.lazierae2.util.GameUtil;
import dev.rlnt.lazierae2.util.IOUtil;
import dev.rlnt.lazierae2.util.TextUtil;
import dev.rlnt.lazierae2.util.TypeEnums;
import dev.rlnt.lazierae2.util.TypeEnums.IO_SETTING;
import dev.rlnt.lazierae2.util.TypeEnums.IO_SIDE;
import dev.rlnt.lazierae2.util.TypeEnums.TRANSLATE_TYPE;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.apache.commons.lang3.ArrayUtils;

public abstract class ProcessorTile<I extends AbstractItemHandler, R extends AbstractRecipe> extends MachineTile<I> {

    public static final int SLOT_UPGRADE = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int INFO_SIZE = 14;
    private final int[] inputSlots;
    private final String id;
    private final AbstractEnergyStorage energyStorage;
    private final LazyOptional<EnergyStorage> energyStorageCap;
    private final LazyOptional<IItemHandlerModifiable>[] sidedInvWrapper;
    private final EnumMap<Direction, LazyOptional<IItemHandler>> cache = new EnumMap<>(Direction.class);
    private boolean autoExtract = false;
    private EnumMap<IO_SIDE, IO_SETTING> sideConfig = new EnumMap<>(IO_SIDE.class);
    private ItemStack currentStack = ItemStack.EMPTY;
    private float progress;
    private int effectiveProcessTime;
    protected final IIntArray info = new IIntArrayIO() {
        @Override
        public int get(int index) {
            switch (index) {
                // the server truncates integers to shorts so it requires splitting
                case 0:
                    // energy lower bytes
                    return energyStorage.getEnergyStored() & 0xFFFF;
                case 1:
                    // energy upper bytes
                    return (energyStorage.getEnergyStored() >> 16) & 0xFFFF;
                case 2:
                    // max energy lower bytes
                    return energyStorage.getMaxEnergyStored() & 0xFFFF;
                case 3:
                    // max energy upper bytes
                    return (energyStorage.getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4:
                    return getEffectiveEnergy();
                case 5:
                    return (int) progress;
                case 6:
                    return effectiveProcessTime;
                case 7:
                    // top side io config
                    return getIOSetting(IO_SIDE.TOP);
                case 8:
                    // left side io config
                    return getIOSetting(IO_SIDE.LEFT);
                case 9:
                    // front side io config
                    return getIOSetting(IO_SIDE.FRONT);
                case 10:
                    // right side io config
                    return getIOSetting(IO_SIDE.RIGHT);
                case 11:
                    // bottom side io config
                    return getIOSetting(IO_SIDE.BOTTOM);
                case 12:
                    // back side io config
                    return getIOSetting(IO_SIDE.BACK);
                case 13:
                    // auto extraction
                    return autoExtract ? 1 : 0;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index >= 0 && index <= 1) energyStorage.setEnergy(value);
            if (index >= 2 && index <= 3) energyStorage.setCapacity(value);
            if (index == 5) progress = value;
            if (index == 6) effectiveProcessTime = value;
        }

        @Override
        public int getCount() {
            return ProcessorTile.INFO_SIZE;
        }

        @Override
        public int getIOSetting(IO_SIDE side) {
            if (sideConfig.size() == 0) return 0;
            return IOUtil.getIOSettingsMap().inverse().get(sideConfig.get(side));
        }
    };
    private ItemStack finishedStack = ItemStack.EMPTY;

    protected ProcessorTile(TileEntityType<?> tileEntityTypeIn, String id, int[] inputSlots) {
        super(tileEntityTypeIn);
        this.id = id;
        this.inputSlots = inputSlots;

        // get initial side configuration
        initSideConfig();

        // set up the energy storage with the config defined buffer capacity
        energyStorage = new AbstractEnergyStorage(this, getEffectiveEnergyCapacity());
        energyStorageCap = LazyOptional.of(() -> energyStorage);

        // create a sided inventory wrapper for the IO capabilities
        sidedInvWrapper =
            SidedInvWrapper.create(
                this,
                Direction.UP,
                Direction.DOWN,
                Direction.NORTH,
                Direction.SOUTH,
                Direction.WEST,
                Direction.EAST
            );
    }

    @Override
    public ITextComponent getDisplayName() {
        return TextUtil.translate(TRANSLATE_TYPE.CONTAINER, id);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        energyStorage.deserializeNBT(nbt.getCompound(ENERGY));
        itemHandler.setStackInSlot(SLOT_UPGRADE, ItemStack.of(nbt.getCompound(UPGRADE)));
        progress = nbt.getFloat(PROGRESS);
        effectiveProcessTime = nbt.getInt(PROCESSING_TIME);
        currentStack = ItemStack.of(nbt.getCompound(STACK_CURRENT));
        finishedStack = ItemStack.of(nbt.getCompound(STACK_OUTPUT));
        sideConfig = (EnumMap<IO_SIDE, IO_SETTING>) IOUtil.getSideConfigFromArray(nbt.getIntArray(IO_CONFIG));
        autoExtract = nbt.getBoolean(AUTO_EXTRACT);
        refreshEnergyCapacity();
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put(ENERGY, energyStorage.serializeNBT());
        nbt.put(UPGRADE, itemHandler.getStackInSlot(SLOT_UPGRADE).save(new CompoundNBT()));
        nbt.putFloat(PROGRESS, progress);
        nbt.putInt(PROCESSING_TIME, effectiveProcessTime);
        nbt.put(STACK_CURRENT, currentStack.save(new CompoundNBT()));
        nbt.put(STACK_OUTPUT, finishedStack.save(new CompoundNBT()));
        nbt.putIntArray(IO_CONFIG, IOUtil.serializeSideConfig(sideConfig));
        nbt.putBoolean(AUTO_EXTRACT, autoExtract);
        return super.save(nbt);
    }

    @Override
    public void setRemoved() {
        energyStorageCap.invalidate();
        for (LazyOptional<IItemHandlerModifiable> sidedInvCap : sidedInvWrapper) {
            sidedInvCap.invalidate();
        }
        super.setRemoved();
    }

    @Override
    protected void invalidateCaps() {
        energyStorageCap.invalidate();
        for (LazyOptional<IItemHandlerModifiable> sidedInvCap : sidedInvWrapper) {
            sidedInvCap.invalidate();
        }
        super.invalidateCaps();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!isRemoved()) {
            if (cap == CapabilityEnergy.ENERGY) return energyStorageCap.cast();
            if (side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                switch (side) {
                    case UP:
                        return sidedInvWrapper[0].cast();
                    case DOWN:
                        return sidedInvWrapper[1].cast();
                    case NORTH:
                        return sidedInvWrapper[2].cast();
                    case SOUTH:
                        return sidedInvWrapper[3].cast();
                    case WEST:
                        return sidedInvWrapper[4].cast();
                    case EAST:
                        return sidedInvWrapper[5].cast();
                }
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide()) return;

        // try to auto extract every 20 ticks (1 second)
        if (autoExtract && level.getGameTime() % 20 == 0) autoExport();

        // get the recipe for the current input
        R recipe = getRecipe();
        if (recipe != null) {
            int requiredEnergy = getEffectiveEnergy();
            if (canWork(recipe, requiredEnergy)) {
                // start work if there is a valid recipe
                doWork(recipe, requiredEnergy);
            } else {
                setActivityState(false);
            }
        } else {
            // reset the processor if there is no valid recipe
            stopWork();
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        // get the respective slots for the current I/O configuration
        Direction facing = getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        if (side == facing) {
            // front
            return getSlotsForIOSide(IO_SIDE.FRONT);
        } else if (side == facing.getClockWise()) {
            // left
            return getSlotsForIOSide(IO_SIDE.LEFT);
        } else if (side == facing.getOpposite()) {
            // back
            return getSlotsForIOSide(IO_SIDE.BACK);
        } else if (side == facing.getCounterClockWise()) {
            // right
            return getSlotsForIOSide(IO_SIDE.RIGHT);
        } else if (side == Direction.UP) {
            // top
            return getSlotsForIOSide(IO_SIDE.TOP);
        } else if (side == Direction.DOWN) {
            // bottom
            return getSlotsForIOSide(IO_SIDE.BOTTOM);
        }

        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        // prevent insertion when it's the output slot or the item is not valid as input
        return (
            index != SLOT_OUTPUT &&
            itemHandler.isItemValid(index, itemStackIn) &&
            (!(itemHandler instanceof MultiItemHandler) || itemHandler.getValidSlot(itemStackIn) == index)
        );
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        // only allow taking items from the output slot
        return index == SLOT_OUTPUT;
    }

    /**
     * Gets the ProcessorRecipe for the current Input of
     * the Processor.
     *
     * @return the ProcessorRecipe
     */
    @Nullable
    private R getRecipe() {
        // level can't be null here anymore so we can assert it
        assert level != null;

        // return null if all input slots are empty
        if (itemHandler instanceof SingleItemHandler) {
            // 1 input slot
            if (itemHandler.getStackInSlot(inputSlots[0]).isEmpty()) return null;
        } else {
            // 3 input slots
            if (Arrays.stream(inputSlots).allMatch(slot -> itemHandler.getStackInSlot(slot).isEmpty())) return null;
        }

        // return recipe for current processor inventory
        return GameUtil.getRecipeManager(level).getRecipeFor(getRecipeType(), this, level).orElse(null);
    }

    /**
     * Resets the Processor to its initial state.
     */
    private void stopWork() {
        // set the progress back to 0
        setActivityState(false);
        effectiveProcessTime = 0;
        progress = 0;
    }

    /**
     * Sets the LIT state of the current processor to represent if it's
     * running or not which determines the texture and the glow.
     * @param state if the processor is active
     */
    private void setActivityState(boolean state) {
        if (level == null) return;
        BlockState currenState = level.getBlockState(worldPosition);
        if (state && currenState.getValue(ProcessorBlock.LIT).equals(false)) {
            updateState(currenState.setValue(ProcessorBlock.LIT, true));
        } else if (!state && currenState.getValue(ProcessorBlock.LIT).equals(true)) {
            R recipe = getRecipe();
            if (recipe == null || !canWork(recipe, getEffectiveEnergy())) updateState(
                currenState.setValue(ProcessorBlock.LIT, false)
            );
        }
    }

    /**
     * Updates the block state of the tile entity to
     * make it user another texture.
     * @param newState the new state
     */
    private void updateState(BlockState newState) {
        if (level == null) return;
        BlockState oldState = level.getBlockState(worldPosition);
        if (oldState != newState) {
            level.setBlock(worldPosition, newState, 3);
            level.sendBlockUpdated(worldPosition, oldState, newState, 3);
        }
    }

    /**
     * Checks if the processor is able to handle the current recipe.
     * @param recipe the recipe that should be processed
     * @return true if the processor can take the recipe, otherwise false
     */
    private boolean canWork(R recipe, int requiredEnergy) {
        currentStack = getItem(SLOT_OUTPUT);
        finishedStack = recipe.assemble(this);

        // check if the processor has enough energy for the operation
        if (requiredEnergy > energyStorage.getEnergyStored()) return false;

        // if the output slot is not empty, try to merge the output of the recipe
        if (!currentStack.isEmpty()) {
            int mergeCount = currentStack.getCount() + finishedStack.getCount();
            return (
                currentStack.sameItem(finishedStack) &&
                mergeCount <= finishedStack.getMaxStackSize() &&
                mergeCount <= itemHandler.getSlotLimit(SLOT_OUTPUT)
            );
        }

        return true;
    }

    /**
     * Handles the work for the current ProcessorRecipe.
     *
     * @param requiredEnergy the amount of energy required for the operation
     */
    private void doWork(R recipe, int requiredEnergy) {
        effectiveProcessTime = getEffectiveProcessTime(recipe.getProcessTime());

        // consume energy
        energyStorage.setEnergy(energyStorage.getEnergyStored() - requiredEnergy);

        // increase the progress if the recipe is not done yet and set the processor to active
        if (progress < effectiveProcessTime) {
            setProgress(progress + 1);
            setActivityState(true);
        }

        // if the progress matches the required time for the recipe, finish the work
        if (progress >= effectiveProcessTime) finishWork();
    }

    /**
     * Handles the final steps for processing the recipe. Sets the output, clears
     * the input and resets the Processor.
     */
    private void finishWork() {
        // adjust output
        if (!currentStack.isEmpty()) {
            currentStack.grow(finishedStack.getCount());
        } else {
            setItem(SLOT_OUTPUT, finishedStack);
        }

        // clear input slots
        if (itemHandler instanceof SingleItemHandler) {
            // 1 input slot
            removeItem(inputSlots[0], 1);
        } else {
            // 3 input slots
            for (int slot : inputSlots) {
                if (getItem(slot).isEmpty()) continue;
                removeItem(slot, 1);
            }
        }

        // reset progress and mark entity as changed
        stopWork();
    }

    private void autoExport() {
        // level can't be null here because it's called from tick()
        assert level != null;

        // only try to export if the output slot has an item
        if (itemHandler.getStackInSlot(SLOT_OUTPUT).isEmpty()) return;

        Direction facing = getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        EnumMap<Direction, TileEntity> outputInventories = new EnumMap<>(Direction.class);

        // fill the adjacent inventory map with possible output inventories
        sideConfig.forEach(
            (side, setting) -> {
                // only check sides which are actually set to be able to output
                if (setting != IO_SETTING.OUTPUT && setting != IO_SETTING.IO) return;
                // decide the direction depending on the IO side
                Direction direction = getDirection(facing, side);
                // add the adjacent inventory on the current side to the map
                outputInventories.put(direction, level.getBlockEntity(worldPosition.relative(direction, 1)));
            }
        );

        // iterate over all possible output inventories and try to push the output items
        for (Map.Entry<Direction, TileEntity> entry : outputInventories.entrySet()) {
            // try to load capability from cache
            LazyOptional<IItemHandler> target = cache.get(entry.getKey());

            // if cached capability was invalidated or wasn't filled yet, cache the current one
            if (target == null) {
                ICapabilityProvider provider = entry.getValue();
                if (provider == null) continue;
                target =
                    provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, entry.getKey().getOpposite());
                cache.put(entry.getKey(), target);
                target.addListener(self -> cache.put(entry.getKey(), null));
            }

            // defines if the loop should stop after the next inventory because the export items are empty
            AtomicBoolean shouldBreak = new AtomicBoolean(false);

            // if the target inventory has a valid inventory capability
            target.ifPresent(
                targetInventory -> {
                    // try to export everything in the output slot to the inventory
                    ItemStack toExport = itemHandler.getStackInSlot(SLOT_OUTPUT);
                    ItemStack remaining = ItemHandlerHelper.insertItemStacked(targetInventory, toExport, false);
                    // mark the tile entity as changed when something was exported
                    if (remaining.getCount() != toExport.getCount() || !remaining.sameItem(toExport)) {
                        // set the stack in the output slot to the remaining stack
                        itemHandler.setStackInSlot(SLOT_OUTPUT, remaining);
                        setChanged();
                    }
                    // break the loop for other output inventories if nothing is left to export
                    if (remaining.isEmpty()) {
                        shouldBreak.set(true);
                    }
                }
            );

            if (shouldBreak.get()) return;
        }
    }

    /**
     * Gets the direction for finding the adjacent inventory for the auto extract.
     * @param facing the direction the processor is currently facing
     * @param side the io side to get the direction for
     * @return the direction of the passed in io side
     */
    private Direction getDirection(Direction facing, IO_SIDE side) {
        Direction direction;
        switch (side) {
            case TOP:
                direction = Direction.UP;
                break;
            case LEFT:
                direction = facing.getClockWise();
                break;
            case FRONT:
                direction = facing;
                break;
            case RIGHT:
                direction = facing.getCounterClockWise();
                break;
            case BOTTOM:
                direction = Direction.DOWN;
                break;
            case BACK:
                direction = facing.getOpposite();
                break;
            default:
                throw new IllegalArgumentException("No side found called " + side);
        }
        return direction;
    }

    /**
     * Refreshes the current energy capacity by calculating
     * the new capacity from the upgrade amount.
     */
    public void refreshEnergyCapacity() {
        energyStorage.setCapacity(getEffectiveEnergyCapacity());
    }

    /**
     * Recalculates values by the current upgrade amount.
     * @param base the base value
     * @param upgrade the upgrade multiplier
     * @param type the calculation typ
     * @return the upgraded value
     */
    protected int getUpgradedValue(int base, double upgrade, TypeEnums.OPERATION_TYPE type) {
        int upgradeAmount = getUpgradeStack().getCount();
        if (type == TypeEnums.OPERATION_TYPE.ADD) {
            return (int) (base + upgrade * upgradeAmount);
        }
        double pow = Math.pow(upgrade, upgradeAmount);
        return (int) (type == TypeEnums.OPERATION_TYPE.MULTI ? base * pow : base / pow);
    }

    /**
     * Checks if the clicked slot is within the processor inventory.
     * @param index the index of the slot which is checked
     * @return true if the slot is within the processor inventory, false otherwise
     */
    public boolean isWithinProcessorSlots(int index) {
        return index < getContainerSize();
    }

    /**
     * Fills the side configuration of the tile entity with
     * initial values in case the deserialization fails.
     */
    private void initSideConfig() {
        for (IO_SIDE side : IO_SIDE.values()) sideConfig.put(side, IO_SETTING.NONE);
    }

    /**
     * Gets the slot indexes for the provided IO side
     * @param side the io side to get the slots for
     * @return the slots exposed on that side
     */
    private int[] getSlotsForIOSide(IO_SIDE side) {
        IO_SETTING setting = sideConfig.get(side);
        switch (setting) {
            case NONE:
                return new int[] {};
            case INPUT:
                return getInputSlots();
            case OUTPUT:
                return new int[] { SLOT_OUTPUT };
            case IO:
                return ArrayUtils.addAll(getInputSlots(), SLOT_OUTPUT);
            default:
                throw new IllegalArgumentException("Can't find slots for side " + side);
        }
    }

    /**
     * Converts the current tile entity to an ItemStack and returns it.
     * It saves some necessary information such as the energy, the
     * upgrades and the current I/O configuration.
     * @return the tile entity as ItemStack
     */
    public ItemStack getBlockItem() {
        CompoundNBT nbt = new CompoundNBT();
        if (energyStorage.getEnergyStored() > 0) nbt.put(ENERGY, energyStorage.serializeNBT());
        if (getUpgradeStack().getCount() > 0) nbt.put(
            UPGRADE,
            itemHandler.getStackInSlot(SLOT_UPGRADE).save(new CompoundNBT())
        );
        if (IOUtil.isChanged(sideConfig)) nbt.putIntArray(IO_CONFIG, IOUtil.serializeSideConfig(sideConfig));
        if (autoExtract) nbt.putBoolean(AUTO_EXTRACT, true);
        ItemStack stack = new ItemStack(getItemProvider());
        if (!nbt.isEmpty()) stack.setTag(nbt);
        return stack;
    }

    public void setSideConfig(Map<IO_SIDE, IO_SETTING> sideConfig) {
        this.sideConfig = (EnumMap<IO_SIDE, IO_SETTING>) sideConfig;
        setChanged();
    }

    private void setProgress(float progress) {
        this.progress = progress;
        setChanged();
    }

    public void toggleAutoExtract() {
        autoExtract = !autoExtract;
    }

    public int[] getInputSlots() {
        return inputSlots;
    }

    public ItemStack getUpgradeStack() {
        if (itemHandler == null) return ItemStack.EMPTY;
        return itemHandler.getStackInSlot(SLOT_UPGRADE);
    }

    public AbstractEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    protected abstract IItemProvider getItemProvider();

    protected abstract IRecipeType<R> getRecipeType();

    protected abstract int getEffectiveEnergyCapacity();

    protected abstract int getEffectiveEnergy();

    protected abstract int getEffectiveProcessTime(int processTime);
}
