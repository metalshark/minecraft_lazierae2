package dev.rlnt.lazierae2.setup;

import static dev.rlnt.lazierae2.Constants.*;

import dev.rlnt.lazierae2.block.AggregatorBlock;
import dev.rlnt.lazierae2.block.CentrifugeBlock;
import dev.rlnt.lazierae2.block.EnergizerBlock;
import dev.rlnt.lazierae2.block.EtcherBlock;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Register and store all blocks and respective blockitems
 */
public class ModBlocks {

    // registry
    static final DeferredRegister<Block> BLOCKS = Registration.createRegistry(ForgeRegistries.BLOCKS);

    // processor blocks
    public static final RegistryObject<Block> AGGREGATOR = register(AGGREGATOR_ID, AggregatorBlock::new);
    public static final RegistryObject<Block> CENTRIFUGE = register(CENTRIFUGE_ID, CentrifugeBlock::new);
    public static final RegistryObject<Block> ENERGIZER = register(ENERGIZER_ID, EnergizerBlock::new);
    public static final RegistryObject<Block> ETCHER = register(ETCHER_ID, EtcherBlock::new);

    private ModBlocks() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Register blocks and their respective blockitems
     *
     * @param name  the name of the block
     * @param block a supplier that returns a block
     * @param <T>   the block type
     * @return the registered block
     */
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> result = BLOCKS.register(name, block);

        ModItems.ITEMS.register(name, () -> new BlockItem(result.get(), new Item.Properties().tab(ModTab.TAB)));

        return result;
    }
}
