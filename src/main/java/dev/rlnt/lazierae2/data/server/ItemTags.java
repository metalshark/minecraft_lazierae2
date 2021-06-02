package dev.rlnt.lazierae2.data.server;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.setup.ModItems;
import dev.rlnt.lazierae2.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(DataGenerator generator, BlockTagsProvider tagProvider, ExistingFileHelper existingFileHelper) {
        super(generator, tagProvider, MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Items.DUSTS_COAL).add(ModItems.COAL_DUST.get());
        tag(ModTags.Items.DUSTS_CARBONIC_FLUIX).add(ModItems.CARB_FLUIX_DUST.get());
        tag(Tags.Items.DUSTS).add(ModItems.COAL_DUST.get()).add(ModItems.CARB_FLUIX_DUST.get());

        tag(ModTags.Items.GEMS_RESONATING).add(ModItems.RESONATING_GEM.get());
        tag(Tags.Items.GEMS).add(ModItems.RESONATING_GEM.get());

        tag(ModTags.Items.INGOTS_FLUIX_IRON).add(ModItems.FLUIX_IRON.get());
        tag(ModTags.Items.INGOTS_FLUIX_STEEL).add(ModItems.FLUIX_STEEL.get());
        tag(Tags.Items.INGOTS).add(ModItems.FLUIX_IRON.get()).add(ModItems.FLUIX_STEEL.get());

        tag(ModTags.Items.PROCESSOR_PARALLEL).add(ModItems.PARALLEL_PROCESSOR.get());
        tag(ModTags.Items.PROCESSOR_SPEC).add(ModItems.SPEC_PROCESSOR.get());
    }
}
