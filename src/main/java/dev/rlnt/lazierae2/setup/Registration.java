package dev.rlnt.lazierae2.setup;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registration {

    private Registration() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Registers all the mod contents.
     * @param modEventBus the mod event bus
     */
    public static void init(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModTiles.TILES.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);
    }

    /**
     * Utility method to quickly create new Deffered Registers.
     * @param registry the original registry to add to
     * @return the created Deffered Register
     */
    static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> createRegistry(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, MOD_ID);
    }
}
