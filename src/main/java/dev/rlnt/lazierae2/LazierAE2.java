package dev.rlnt.lazierae2;

import static dev.rlnt.lazierae2.Constants.MOD_ID;
import static net.minecraftforge.fml.config.ModConfig.Type.COMMON;

import appeng.api.config.Upgrades;
import dev.rlnt.lazierae2.network.PacketHandler;
import dev.rlnt.lazierae2.screen.AggregatorScreen;
import dev.rlnt.lazierae2.screen.CentrifugeScreen;
import dev.rlnt.lazierae2.screen.EnergizerScreen;
import dev.rlnt.lazierae2.screen.EtcherScreen;
import dev.rlnt.lazierae2.setup.ModBlocks;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.setup.ModContainers;
import dev.rlnt.lazierae2.setup.Registration;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MOD_ID)
public class LazierAE2 {

    public LazierAE2() {
        ModLoadingContext context = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // register config
        context.registerConfig(COMMON, ModConfig.processingSpec);
        // register setup listener
        modEventBus.addListener(this::setup);
        // register client listener
        modEventBus.addListener(this::doClientStuff);
        // register mod objects
        Registration.init(modEventBus);
    }

    private void setup(FMLCommonSetupEvent event) {
        // add processors to compatible acceleration card blocks
        int maxUpgrades = ModConfig.PROCESSING.processorUpgradeSlots.get();
        Upgrades.SPEED.registerItem(ModBlocks.AGGREGATOR.get().asItem(), maxUpgrades);
        Upgrades.SPEED.registerItem(ModBlocks.CENTRIFUGE.get().asItem(), maxUpgrades);
        Upgrades.SPEED.registerItem(ModBlocks.ENERGIZER.get().asItem(), maxUpgrades);
        Upgrades.SPEED.registerItem(ModBlocks.ETCHER.get().asItem(), maxUpgrades);
        // initialize packet handler
        PacketHandler.init();
    }

    private void doClientStuff(FMLClientSetupEvent event) {
        // register container screens (GUIs)
        ScreenManager.register(ModContainers.AGGREGATOR.get(), AggregatorScreen::new);
        ScreenManager.register(ModContainers.CENTRIFUGE.get(), CentrifugeScreen::new);
        ScreenManager.register(ModContainers.ENERGIZER.get(), EnergizerScreen::new);
        ScreenManager.register(ModContainers.ETCHER.get(), EtcherScreen::new);
    }
}
