package dev.rlnt.lazierae2.setup;

import static dev.rlnt.lazierae2.Constants.*;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfig {

    public static final Processing PROCESSING;
    public static final ForgeConfigSpec processingSpec;

    static {
        Pair<Processing, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Processing::new);
        PROCESSING = specPair.getLeft();
        processingSpec = specPair.getRight();
    }

    private ModConfig() {
        throw new IllegalStateException("Config class");
    }

    public static class Processing {

        // entries
        static final String ENERGY_BUFFER = "energyBuffer";
        static final String ENERGY_BUFFER_UPGRADE = "energyBufferUpgrade";
        static final String ENERGY_BUFFER_CALCULATION = "Calculation: bufferBase + bufferUpgrade * upgradeAmount)";
        static final String ENERGY_COST_BASE = "energyCostBase";
        static final String ENERGY_COST_UPGRADE = "energyCostUpgrade";
        static final String ENERGY_COST_CALCULATION = "Calculation: energyBase * (energyUpgrade ^ upgradeAmount)";
        static final String WORKER_TICKS_BASE = "workerTicksBase";
        static final String WORKER_TICKS_UPGRADE = "workerTicksUpgrade";
        static final String WORKER_TICKS_USAGE = "This value will be used on recipes with no explicit processing time.";
        static final String WORKER_TICKS_EFFECT = "This only takes effect when custom recipes are added.";
        static final String WORKER_TICKS_CALCULATION = "Calculation: speedBase * (speedUpgrade ^ upgradeAmount)";
        static final String WORKER_TICKS_SPEED =
            "The speed is the reverse value of the processing time. The higher the time, the lower the speed.";
        // general
        public final ForgeConfigSpec.IntValue processorUpgradeSlots;
        // aggregator
        public final ForgeConfigSpec.IntValue aggregatorEnergyBuffer;
        public final ForgeConfigSpec.IntValue aggregatorEnergyBufferUpgrade;
        public final ForgeConfigSpec.IntValue aggregatorEnergyCostBase;
        public final ForgeConfigSpec.DoubleValue aggregatorEnergyCostUpgrade;
        public final ForgeConfigSpec.IntValue aggregatorWorkTicksBase;
        public final ForgeConfigSpec.DoubleValue aggregatorWorkTicksUpgrade;
        // centrifuge
        public final ForgeConfigSpec.IntValue centrifugeEnergyBuffer;
        public final ForgeConfigSpec.IntValue centrifugeEnergyBufferUpgrade;
        public final ForgeConfigSpec.IntValue centrifugeEnergyCostBase;
        public final ForgeConfigSpec.DoubleValue centrifugeEnergyCostUpgrade;
        public final ForgeConfigSpec.IntValue centrifugeWorkTicksBase;
        public final ForgeConfigSpec.DoubleValue centrifugeWorkTicksUpgrade;
        // energizer
        public final ForgeConfigSpec.IntValue energizerEnergyBuffer;
        public final ForgeConfigSpec.IntValue energizerEnergyBufferUpgrade;
        public final ForgeConfigSpec.IntValue energizerEnergyCostBase;
        public final ForgeConfigSpec.DoubleValue energizerEnergyCostUpgrade;
        public final ForgeConfigSpec.IntValue energizerWorkTicksBase;
        public final ForgeConfigSpec.DoubleValue energizerWorkTicksUpgrade;
        // etcher
        public final ForgeConfigSpec.IntValue etcherEnergyBuffer;
        public final ForgeConfigSpec.IntValue etcherEnergyBufferUpgrade;
        public final ForgeConfigSpec.IntValue etcherEnergyCostBase;
        public final ForgeConfigSpec.DoubleValue etcherEnergyCostUpgrade;
        public final ForgeConfigSpec.IntValue etcherWorkTicksBase;
        public final ForgeConfigSpec.DoubleValue etcherWorkTicksUpgrade;

        Processing(ForgeConfigSpec.Builder builder) {
            // region information
            builder.comment(
                "#########################################################################################",
                "You can edit a lot of values for the different processors of the mod here.",
                "",
                "When the config mentions 'processors', all machines which produce materials are meant.",
                "Processors: Fluix Aggregator, Pulse Centrifuge, Crystal Energizer, Circuit Etcher",
                "",
                "When there is an option writing about 'upgrades', Acceleration Cards from AE2 are meant.",
                "#########################################################################################"
            );
            // endregion information

            //region general
            builder.push("general");
            processorUpgradeSlots =
                builder
                    .comment(
                        "The maximum amount of acceleration cards which can be stored in a processor.",
                        "Upgrades will speed up the processing and increase the energy buffer at the cost of a higher energy consumption."
                    )
                    .translation(translationKey("processor", "upgradeSlots"))
                    .defineInRange("upgradeSlots", 8, 1, Integer.MAX_VALUE);
            builder.pop();
            //endregion general

            // region fluix-aggregator
            builder.push(AGGREGATOR_ID);
            aggregatorEnergyBuffer =
                builder
                    .comment("The base size of the Fluix Aggregator's energy buffer.")
                    .translation(translationKey(AGGREGATOR_ID, ENERGY_BUFFER))
                    .defineInRange(ENERGY_BUFFER, 200_000, 1, Integer.MAX_VALUE);
            aggregatorEnergyBufferUpgrade =
                builder
                    .comment(
                        "The additional size the Fluix Aggregator's energy buffer increases per upgrade.",
                        ENERGY_BUFFER_CALCULATION
                    )
                    .translation(translationKey(AGGREGATOR_ID, ENERGY_BUFFER_UPGRADE))
                    .defineInRange(ENERGY_BUFFER_UPGRADE, 100_000, 1, Integer.MAX_VALUE);
            aggregatorEnergyCostBase =
                builder
                    .comment("The base energy cost per tick of the Fluix Aggregator.")
                    .translation(translationKey(AGGREGATOR_ID, ENERGY_COST_BASE))
                    .defineInRange(ENERGY_COST_BASE, 100, 1, Integer.MAX_VALUE);
            aggregatorEnergyCostUpgrade =
                builder
                    .comment(
                        "The multiplicator of how much the Fluix Aggregator's energy cost per tick increases per upgrade.",
                        ENERGY_COST_CALCULATION
                    )
                    .translation(translationKey(AGGREGATOR_ID, ENERGY_COST_UPGRADE))
                    .defineInRange(ENERGY_COST_UPGRADE, 1.3, 1.0, Double.MAX_VALUE);
            aggregatorWorkTicksBase =
                builder
                    .comment(
                        "The base number of ticks needed to process one Fluix Aggregator recipe.",
                        WORKER_TICKS_USAGE,
                        WORKER_TICKS_EFFECT
                    )
                    .translation(translationKey(AGGREGATOR_ID, WORKER_TICKS_BASE))
                    .defineInRange(WORKER_TICKS_BASE, 80, 1, Integer.MAX_VALUE);
            aggregatorWorkTicksUpgrade =
                builder
                    .comment(
                        "The multiplicator of how much the Fluix Aggregator's processing speed per recipe increases per upgrade.",
                        WORKER_TICKS_CALCULATION,
                        WORKER_TICKS_SPEED
                    )
                    .translation(translationKey(AGGREGATOR_ID, WORKER_TICKS_UPGRADE))
                    .defineInRange(WORKER_TICKS_UPGRADE, 1.25, 1.0, Double.MAX_VALUE);
            builder.pop();
            // endregion fluix-aggregator

            // region pulse-centrifuge
            builder.push(CENTRIFUGE_ID);
            centrifugeEnergyBuffer =
                builder
                    .comment("The base size of the Pulse Centrifuge's energy buffer.")
                    .translation(translationKey(CENTRIFUGE_ID, ENERGY_BUFFER))
                    .defineInRange(ENERGY_BUFFER, 400_000, 1, Integer.MAX_VALUE);
            centrifugeEnergyBufferUpgrade =
                builder
                    .comment(
                        "The additional size the Pulse Centrifuge's energy buffer increases per upgrade.",
                        ENERGY_BUFFER_CALCULATION
                    )
                    .translation(translationKey(CENTRIFUGE_ID, ENERGY_BUFFER_UPGRADE))
                    .defineInRange(ENERGY_BUFFER_UPGRADE, 200_000, 1, Integer.MAX_VALUE);
            centrifugeEnergyCostBase =
                builder
                    .comment("The base energy cost per tick of the Pulse Centrifuge.")
                    .translation(translationKey(CENTRIFUGE_ID, ENERGY_COST_BASE))
                    .defineInRange(ENERGY_COST_BASE, 150, 1, Integer.MAX_VALUE);
            centrifugeEnergyCostUpgrade =
                builder
                    .comment(
                        "The multiplicator of how much the Pulse Centrifuge's energy cost per tick increases per upgrade.",
                        ENERGY_COST_CALCULATION
                    )
                    .translation(translationKey(CENTRIFUGE_ID, ENERGY_COST_UPGRADE))
                    .defineInRange(ENERGY_COST_UPGRADE, 1.3, 1.0, Double.MAX_VALUE);
            centrifugeWorkTicksBase =
                builder
                    .comment(
                        "The base number of ticks needed to process one Pulse Centrifuge recipe.",
                        WORKER_TICKS_USAGE,
                        WORKER_TICKS_EFFECT
                    )
                    .translation(translationKey(CENTRIFUGE_ID, WORKER_TICKS_BASE))
                    .defineInRange(WORKER_TICKS_BASE, 160, 1, Integer.MAX_VALUE);
            centrifugeWorkTicksUpgrade =
                builder
                    .comment(
                        "The multiplicator of how much the Pulse Centrifuge's processing speed per recipe increases per upgrade.",
                        WORKER_TICKS_CALCULATION,
                        WORKER_TICKS_SPEED
                    )
                    .translation(translationKey(CENTRIFUGE_ID, WORKER_TICKS_UPGRADE))
                    .defineInRange(WORKER_TICKS_UPGRADE, 1.25, 1.0, Double.MAX_VALUE);
            builder.pop();
            // endregion pulse-centrifuge

            // region crystal-energizer
            builder.push(ENERGIZER_ID);
            energizerEnergyBuffer =
                builder
                    .comment("The base size of the Crystal Energizer's energy buffer.")
                    .translation(translationKey(ENERGIZER_ID, ENERGY_BUFFER))
                    .defineInRange(ENERGY_BUFFER, 200_000, 1, Integer.MAX_VALUE);
            energizerEnergyBufferUpgrade =
                builder
                    .comment(
                        "The additional size the Crystal Energizer's energy buffer increases per upgrade.",
                        ENERGY_BUFFER_CALCULATION
                    )
                    .translation(translationKey(ENERGIZER_ID, ENERGY_BUFFER_UPGRADE))
                    .defineInRange(ENERGY_BUFFER_UPGRADE, 100_000, 1, Integer.MAX_VALUE);
            energizerEnergyCostBase =
                builder
                    .comment("The base energy cost per tick of the Crystal Energizer.")
                    .translation(translationKey(ENERGIZER_ID, ENERGY_COST_BASE))
                    .defineInRange(ENERGY_COST_BASE, 60, 1, Integer.MAX_VALUE);
            energizerEnergyCostUpgrade =
                builder
                    .comment(
                        "The multiplicator of how much the Crystal Energizer's energy cost per tick increases per upgrade.",
                        ENERGY_COST_CALCULATION
                    )
                    .translation(translationKey(ENERGIZER_ID, ENERGY_COST_UPGRADE))
                    .defineInRange(ENERGY_COST_UPGRADE, 1.35, 1.0, Double.MAX_VALUE);
            energizerWorkTicksBase =
                builder
                    .comment(
                        "The base number of ticks needed to process one Crystal Energizer recipe.",
                        WORKER_TICKS_USAGE,
                        WORKER_TICKS_EFFECT
                    )
                    .translation(translationKey(ENERGIZER_ID, WORKER_TICKS_BASE))
                    .defineInRange(WORKER_TICKS_BASE, 100, 1, Integer.MAX_VALUE);
            energizerWorkTicksUpgrade =
                builder
                    .comment(
                        "The multiplicator of how much the Crystal Energizer's processing speed per recipe increases per upgrade.",
                        WORKER_TICKS_CALCULATION,
                        WORKER_TICKS_SPEED
                    )
                    .translation(translationKey(ENERGIZER_ID, WORKER_TICKS_UPGRADE))
                    .defineInRange(WORKER_TICKS_UPGRADE, 1.3, 1.0, Double.MAX_VALUE);
            builder.pop();
            // endregion crystal-energizer

            // region circuit-etcher
            builder.push(ETCHER_ID);
            etcherEnergyBuffer =
                builder
                    .comment("The base size of the Circuit Etcher's energy buffer.")
                    .translation(translationKey(ETCHER_ID, ENERGY_BUFFER))
                    .defineInRange(ENERGY_BUFFER, 400_000, 1, Integer.MAX_VALUE);
            etcherEnergyBufferUpgrade =
                builder
                    .comment(
                        "The additional size the Circuit Etcher's energy buffer increases per upgrade.",
                        ENERGY_BUFFER_CALCULATION
                    )
                    .translation(translationKey(ETCHER_ID, ENERGY_BUFFER_UPGRADE))
                    .defineInRange(ENERGY_BUFFER_UPGRADE, 200_000, 1, Integer.MAX_VALUE);
            etcherEnergyCostBase =
                builder
                    .comment("The base energy cost per tick of the Circuit Etcher.")
                    .translation(translationKey(ETCHER_ID, ENERGY_COST_BASE))
                    .defineInRange(ENERGY_COST_BASE, 250, 1, Integer.MAX_VALUE);
            etcherEnergyCostUpgrade =
                builder
                    .comment(
                        "The multiplicator of how much the Circuit Etcher's energy cost per tick increases per upgrade.",
                        ENERGY_COST_CALCULATION
                    )
                    .translation(translationKey(ETCHER_ID, ENERGY_COST_UPGRADE))
                    .defineInRange(ENERGY_COST_UPGRADE, 1.26, 1.0, Double.MAX_VALUE);
            etcherWorkTicksBase =
                builder
                    .comment(
                        "The base number of ticks needed to process one Circuit Etcher recipe.",
                        WORKER_TICKS_USAGE,
                        WORKER_TICKS_EFFECT
                    )
                    .translation(translationKey(ETCHER_ID, WORKER_TICKS_BASE))
                    .defineInRange(WORKER_TICKS_BASE, 120, 1, Integer.MAX_VALUE);
            etcherWorkTicksUpgrade =
                builder
                    .comment(
                        "The multiplicator of how much the Circuit Etcher's processing speed per recipe increases per upgrade.",
                        WORKER_TICKS_CALCULATION,
                        WORKER_TICKS_SPEED
                    )
                    .translation(translationKey(ETCHER_ID, WORKER_TICKS_UPGRADE))
                    .defineInRange(WORKER_TICKS_UPGRADE, 1.2, 1.0, Double.MAX_VALUE);
            builder.pop();
            // endregion circuit-etcher
        }

        private static String translationKey(String machine, String id) {
            return MOD_ID + ".config." + machine + "." + id;
        }
    }
}
