package dev.rlnt.lazierae2.setup;

import static dev.rlnt.lazierae2.Constants.*;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    // registry
    static final DeferredRegister<Item> ITEMS = Registration.createRegistry(ForgeRegistries.ITEMS);

    // items
    public static final RegistryObject<Item> COAL_DUST = simpleItem(COAL_DUST_ID);
    public static final RegistryObject<Item> CARB_FLUIX_DUST = simpleItem(CARB_FLUIX_DUST_ID);
    public static final RegistryObject<Item> RESONATING_GEM = simpleItem(RESONATING_GEM_ID);
    public static final RegistryObject<Item> FLUIX_IRON = simpleItem(FLUIX_IRON_ID);
    public static final RegistryObject<Item> FLUIX_STEEL = simpleItem(FLUIX_STEEL_ID);
    public static final RegistryObject<Item> LOGIC_UNIT = simpleItem(LOGIC_UNIT_ID);
    public static final RegistryObject<Item> UNIVERSAL_PRESS = simpleItem(UNIVERSAL_PRESS_ID);
    public static final RegistryObject<Item> PARALLEL_PRINTED = simpleItem(PARALLEL_PRINTED_ID);
    public static final RegistryObject<Item> SPEC_PRINTED = simpleItem(SPEC_PRINTED_ID);
    public static final RegistryObject<Item> PARALLEL_PROCESSOR = simpleItem(PARALLEL_PROCESSOR_ID);
    public static final RegistryObject<Item> SPEC_PROCESSOR = simpleItem(SPEC_PROCESSOR_ID);
    public static final RegistryObject<Item> SPEC_CORE_1 = simpleItem(SPEC_CORE_1_ID);
    public static final RegistryObject<Item> SPEC_CORE_2 = simpleItem(SPEC_CORE_2_ID);
    public static final RegistryObject<Item> SPEC_CORE_4 = simpleItem(SPEC_CORE_4_ID);
    public static final RegistryObject<Item> SPEC_CORE_8 = simpleItem(SPEC_CORE_8_ID);
    public static final RegistryObject<Item> SPEC_CORE_16 = simpleItem(SPEC_CORE_16_ID);
    public static final RegistryObject<Item> SPEC_CORE_32 = simpleItem(SPEC_CORE_32_ID);
    public static final RegistryObject<Item> SPEC_CORE_64 = simpleItem(SPEC_CORE_64_ID);

    private ModItems() {
        throw new IllegalStateException("Utility class");
    }

    private static RegistryObject<Item> simpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(ModTab.TAB)));
    }
}
