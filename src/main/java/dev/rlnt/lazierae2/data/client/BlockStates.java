package dev.rlnt.lazierae2.data.client;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.setup.ModBlocks;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockStates extends BlockStateProvider {

    private static final String MACHINE_BLOCK_TEXTURE = "blocks/machine";

    public BlockStates(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerMachine(ModBlocks.AGGREGATOR);
        registerMachine(ModBlocks.CENTRIFUGE);
        registerMachine(ModBlocks.ENERGIZER);
        registerMachine(ModBlocks.ETCHER);
    }

    private void registerMachine(RegistryObject<Block> machine) {
        String name = Objects.requireNonNull(machine.get().getRegistryName()).toString().substring(MOD_ID.length() + 1);
        ResourceLocation mainRL = new ResourceLocation(MOD_ID, MACHINE_BLOCK_TEXTURE);
        ResourceLocation frontRL = new ResourceLocation(MOD_ID, "blocks/" + name);
        ResourceLocation frontRLActive = new ResourceLocation(MOD_ID, "blocks/" + name + "_active");
        BlockModelBuilder modelMachine = models()
            .cube(name, mainRL, mainRL, frontRL, mainRL, mainRL, mainRL)
            .texture("particle", modLoc(MACHINE_BLOCK_TEXTURE));
        BlockModelBuilder modelMachineActive = models()
            .cube(name + "_active", mainRL, mainRL, frontRLActive, mainRL, mainRL, mainRL)
            .texture("particle", modLoc(MACHINE_BLOCK_TEXTURE));

        orientedBlock(
            machine.get(),
            state -> {
                if (state.getValue(BlockStateProperties.LIT).equals(Boolean.TRUE)) {
                    return modelMachineActive;
                } else {
                    return modelMachine;
                }
            }
        );
    }

    private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
            .forAllStates(
                state -> {
                    Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

                    return ConfiguredModel
                        .builder()
                        .modelFile(modelFunc.apply(state))
                        .rotationX(dir.getAxis() == Direction.Axis.Y ? dir.getAxisDirection().getStep() * -90 : 0)
                        .rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.get2DDataValue() + 2) % 4) * 90 : 0)
                        .build();
                }
            );
    }
}
