package dev.rlnt.lazierae2.data;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.data.client.BlockStates;
import dev.rlnt.lazierae2.data.client.ItemModels;
import dev.rlnt.lazierae2.data.server.BlockTags;
import dev.rlnt.lazierae2.data.server.ItemTags;
import dev.rlnt.lazierae2.data.server.ModRecipes;
import dev.rlnt.lazierae2.data.server.Recipes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators() {
        throw new IllegalStateException("Utility Class");
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            // generate tags
            BlockTags blockTags = new BlockTags(generator, fileHelper);
            generator.addProvider(blockTags);
            generator.addProvider(new ItemTags(generator, blockTags, fileHelper));
            // generate recipes
            generator.addProvider(new Recipes(generator));
            generator.addProvider(new ModRecipes(generator));
        }
        if (event.includeClient()) {
            // generate blockstates
            generator.addProvider(new BlockStates(generator, fileHelper));
            // generate models
            generator.addProvider(new ItemModels(generator, fileHelper));
        }
    }
}
