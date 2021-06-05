package dev.rlnt.lazierae2.setup;

import static dev.rlnt.lazierae2.Constants.*;

import dev.rlnt.lazierae2.Constants;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    private static final String UTILITY_CLASS = "Utility class";

    private ModTags() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static final class Blocks {

        private static final String MACHINE_ENTRY = "machines/";
        public static final ITag.INamedTag<Block> MACHINES_FLUIX_AGGREGATOR = mod(MACHINE_ENTRY + AGGREGATOR_ID);
        public static final ITag.INamedTag<Block> MACHINES_PULSE_CENTRIFUGE = mod(MACHINE_ENTRY + CENTRIFUGE_ID);
        public static final ITag.INamedTag<Block> MACHINES_CRYSTAL_ENERGIZER = mod(MACHINE_ENTRY + ENERGIZER_ID);
        public static final ITag.INamedTag<Block> MACHINES_CIRCUIT_ETCHER = mod(MACHINE_ENTRY + ETCHER_ID);

        private Blocks() {
            throw new IllegalStateException(ModTags.UTILITY_CLASS);
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(Constants.MOD_ID, path).toString());
        }
    }

    public static final class Items {

        public static final ITag.INamedTag<Item> DUSTS_COAL = forge("dusts/coal");
        public static final ITag.INamedTag<Item> DUSTS_CARBONIC_FLUIX = forge("dusts/carbonic_fluix");
        public static final ITag.INamedTag<Item> GEMS_RESONATING = forge("gems/resonating");
        public static final ITag.INamedTag<Item> INGOTS_FLUIX_IRON = forge("ingots/fluix_iron");
        public static final ITag.INamedTag<Item> INGOTS_FLUIX_STEEL = forge("ingots/fluix_steel");

        public static final ITag.INamedTag<Item> PROCESSOR_PARALLEL = mod("processors/parallel");
        public static final ITag.INamedTag<Item> PROCESSOR_SPEC = mod("processors/speculative");

        // Applied Energistics 2
        public static final ITag.INamedTag<Item> SILICON = ItemTags.bind("forge:silicon");

        private Items() {
            throw new IllegalStateException(ModTags.UTILITY_CLASS);
        }

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(Constants.MOD_ID, path).toString());
        }
    }
}
