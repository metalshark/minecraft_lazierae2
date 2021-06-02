package dev.rlnt.lazierae2.data.server;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.setup.ModBlocks;
import dev.rlnt.lazierae2.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTags extends BlockTagsProvider {

    public BlockTags(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.MACHINES_FLUIX_AGGREGATOR).add(ModBlocks.AGGREGATOR.get());
        tag(ModTags.Blocks.MACHINES_PULSE_CENTRIFUGE).add(ModBlocks.CENTRIFUGE.get());
        tag(ModTags.Blocks.MACHINES_CRYSTAL_ENERGIZER).add(ModBlocks.ENERGIZER.get());
        tag(ModTags.Blocks.MACHINES_CIRCUIT_ETCHER).add(ModBlocks.ETCHER.get());
    }
}
