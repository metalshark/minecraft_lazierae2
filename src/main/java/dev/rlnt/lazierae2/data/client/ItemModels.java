package dev.rlnt.lazierae2.data.client;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.setup.ModBlocks;
import dev.rlnt.lazierae2.setup.ModItems;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // blockitems
        existingParent(ModBlocks.AGGREGATOR);
        existingParent(ModBlocks.CENTRIFUGE);
        existingParent(ModBlocks.ENERGIZER);
        existingParent(ModBlocks.ETCHER);

        // items
        builder(ModItems.COAL_DUST);
        builder(ModItems.CARB_FLUIX_DUST);
        builder(ModItems.RESONATING_GEM);
        builder(ModItems.FLUIX_IRON);
        builder(ModItems.FLUIX_STEEL);
        builder(ModItems.LOGIC_UNIT);
        builder(ModItems.PARALLEL_PROCESSOR);
        builder(ModItems.SPEC_PROCESSOR);
        builder(ModItems.SPEC_CORE_1);
        builder(ModItems.SPEC_CORE_2);
        builder(ModItems.SPEC_CORE_4);
        builder(ModItems.SPEC_CORE_8);
        builder(ModItems.SPEC_CORE_16);
        builder(ModItems.SPEC_CORE_32);
        builder(ModItems.SPEC_CORE_64);
    }

    private void existingParent(RegistryObject<Block> block) {
        String name = Objects.requireNonNull(block.get().getRegistryName()).toString().substring(MOD_ID.length() + 1);
        withExistingParent(name, modLoc("block/" + name));
    }

    private void builder(RegistryObject<Item> item) {
        String name = Objects.requireNonNull(item.get().getRegistryName()).toString().substring(MOD_ID.length() + 1);
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        getBuilder(name).parent(itemGenerated).texture("layer0", "items/" + name);
    }
}
