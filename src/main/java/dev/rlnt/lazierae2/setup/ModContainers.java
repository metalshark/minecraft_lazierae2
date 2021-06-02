package dev.rlnt.lazierae2.setup;

import static dev.rlnt.lazierae2.Constants.*;

import dev.rlnt.lazierae2.container.AggregatorContainer;
import dev.rlnt.lazierae2.container.CentrifugeContainer;
import dev.rlnt.lazierae2.container.EnergizerContainer;
import dev.rlnt.lazierae2.container.EtcherContainer;
import dev.rlnt.lazierae2.tile.AggregatorTile;
import dev.rlnt.lazierae2.tile.CentrifugeTile;
import dev.rlnt.lazierae2.tile.EnergizerTile;
import dev.rlnt.lazierae2.tile.EtcherTile;
import java.util.Objects;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Register and store all containers
 */
public class ModContainers {

    // registry
    static final DeferredRegister<ContainerType<?>> CONTAINERS = Registration.createRegistry(
        ForgeRegistries.CONTAINERS
    );

    // processor containers
    public static final RegistryObject<ContainerType<AggregatorContainer>> AGGREGATOR = CONTAINERS.register(
        AGGREGATOR_ID,
        () ->
            IForgeContainerType.create(
                (windowId, inv, data) -> {
                    BlockPos pos = data.readBlockPos();
                    AggregatorTile tile = (AggregatorTile) inv.player.level.getBlockEntity(pos);
                    return new AggregatorContainer(windowId, inv, Objects.requireNonNull(tile));
                }
            )
    );
    public static final RegistryObject<ContainerType<CentrifugeContainer>> CENTRIFUGE = CONTAINERS.register(
        CENTRIFUGE_ID,
        () ->
            IForgeContainerType.create(
                (windowId, inv, data) -> {
                    BlockPos pos = data.readBlockPos();
                    CentrifugeTile tile = (CentrifugeTile) inv.player.level.getBlockEntity(pos);
                    return new CentrifugeContainer(windowId, inv, Objects.requireNonNull(tile));
                }
            )
    );
    public static final RegistryObject<ContainerType<EnergizerContainer>> ENERGIZER = CONTAINERS.register(
        ENERGIZER_ID,
        () ->
            IForgeContainerType.create(
                (windowId, inv, data) -> {
                    BlockPos pos = data.readBlockPos();
                    EnergizerTile tile = (EnergizerTile) inv.player.level.getBlockEntity(pos);
                    return new EnergizerContainer(windowId, inv, Objects.requireNonNull(tile));
                }
            )
    );
    public static final RegistryObject<ContainerType<EtcherContainer>> ETCHER = CONTAINERS.register(
        ETCHER_ID,
        () ->
            IForgeContainerType.create(
                (windowId, inv, data) -> {
                    BlockPos pos = data.readBlockPos();
                    EtcherTile tile = (EtcherTile) inv.player.level.getBlockEntity(pos);
                    return new EtcherContainer(windowId, inv, Objects.requireNonNull(tile));
                }
            )
    );

    private ModContainers() {
        throw new IllegalStateException("Utility class");
    }
}
