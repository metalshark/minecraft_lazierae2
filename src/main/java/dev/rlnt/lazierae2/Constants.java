package dev.rlnt.lazierae2;

public final class Constants {

    // mod info
    public static final String MOD_ID = "lazierae2";
    // nbt info tile entity
    public static final String INVENTORY = "inventory";
    public static final String ENERGY = "energy";
    public static final String UPGRADE = "upgrade";
    public static final String PROGRESS = "progress";
    public static final String STACK_CURRENT = "stack_current";
    public static final String STACK_OUTPUT = "stack_output";
    public static final String IO_CONFIG = "io_config";
    public static final String AUTO_EXTRACT = "auto_extract";
    // nbt info recipe
    public static final String INPUT = "input";
    public static final String INPUTS = "inputs";
    public static final String OUTPUT = "output";
    public static final String PROCESSING_TIME = "processing_time";
    public static final String ITEM = "item";
    public static final String COUNT = "count";
    // utility
    public static final String NETWORK = "network";
    // block ids
    public static final String GROWTH_CHAMBER_ID = "growth_chamber";
    // processor ids
    public static final String AGGREGATOR_ID = "fluix_aggregator";
    public static final String CENTRIFUGE_ID = "pulse_centrifuge";
    public static final String ENERGIZER_ID = "crystal_energizer";
    public static final String ETCHER_ID = "circuit_etcher";
    // item ids
    public static final String COAL_DUST_ID = "coal_dust";
    public static final String CARB_FLUIX_DUST_ID = "carb_fluix_dust";
    public static final String RESONATING_GEM_ID = "resonating_gem";
    public static final String FLUIX_IRON_ID = "fluix_iron_ingot";
    public static final String FLUIX_STEEL_ID = "fluix_steel_ingot";
    public static final String LOGIC_UNIT_ID = "logic_unit";
    public static final String UNIVERSAL_PRESS_ID = "universal_press";
    public static final String PARALLEL_PRINTED_ID = "parallel_printed";
    public static final String PARALLEL_PROCESSOR_ID = "parallel_processor";
    public static final String SPEC_PRINTED_ID = "speculative_printed";
    public static final String SPEC_PROCESSOR_ID = "speculative_processor";
    public static final String SPEC_CORE_1_ID = "spec_core_1";
    public static final String SPEC_CORE_2_ID = "spec_core_2";
    public static final String SPEC_CORE_4_ID = "spec_core_4";
    public static final String SPEC_CORE_8_ID = "spec_core_8";
    public static final String SPEC_CORE_16_ID = "spec_core_16";
    public static final String SPEC_CORE_32_ID = "spec_core_32";
    public static final String SPEC_CORE_64_ID = "spec_core_64";

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
