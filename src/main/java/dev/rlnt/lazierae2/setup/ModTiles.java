package dev.rlnt.lazierae2.setup;

import static dev.rlnt.lazierae2.Constants.*;

import dev.rlnt.lazierae2.tile.AggregatorTile;
import dev.rlnt.lazierae2.tile.CentrifugeTile;
import dev.rlnt.lazierae2.tile.EnergizerTile;
import dev.rlnt.lazierae2.tile.EtcherTile;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Register and store all tile entities
 */
public class ModTiles {

    // registry
    static final DeferredRegister<TileEntityType<?>> TILES = Registration.createRegistry(ForgeRegistries.TILE_ENTITIES);

    // processor tiles
    public static final RegistryObject<TileEntityType<AggregatorTile>> AGGREGATOR = register(
        AGGREGATOR_ID,
        AggregatorTile::new,
        ModBlocks.AGGREGATOR
    );
    public static final RegistryObject<TileEntityType<CentrifugeTile>> CENTRIFUGE = register(
        CENTRIFUGE_ID,
        CentrifugeTile::new,
        ModBlocks.CENTRIFUGE
    );
    public static final RegistryObject<TileEntityType<EnergizerTile>> ENERGIZER = register(
        ENERGIZER_ID,
        EnergizerTile::new,
        ModBlocks.ENERGIZER
    );
    public static final RegistryObject<TileEntityType<EtcherTile>> ETCHER = register(
        ETCHER_ID,
        EtcherTile::new,
        ModBlocks.ETCHER
    );

    private ModTiles() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Register a tile entity
     *
     * @param name  the name of the entity
     * @param tile  the tile to register
     * @param block the block that this tile is for
     * @param <T>   the tile type
     * @param <B>   the block type
     * @return the registered tile entity
     */
    private static <T extends TileEntity, B extends Block> RegistryObject<TileEntityType<T>> register(
        String name,
        Supplier<T> tile,
        RegistryObject<B> block
    ) {
        //noinspection ConstantConditions
        return TILES.register(name, () -> TileEntityType.Builder.of(tile, block.get()).build(null));
    }
}
