package com.enn3developer.naddons;

import com.enn3developer.naddons.blocks.Blocks;
import com.enn3developer.naddons.items.Items;
import com.enn3developer.naddons.menus.Menus;
import com.enn3developer.naddons.screens.EnergyCounterScreen;
import com.enn3developer.naddons.tiles.BlockEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NAddons.MODID)
public class NAddons {
    public static final String MODID = "naddons";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Blocks BLOCKS = new Blocks();
    public static final Items ITEMS = new Items();
    public static final BlockEntities BLOCK_ENTITIES = new BlockEntities();
    public static final Menus MENUS = new Menus();

    public NAddons() {
        LOGGER.info("N is injecting malicious code in your modpack");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        ITEMS.register(modEventBus);
        MENUS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> MenuScreens.register(MENUS.ENERGY_COUNTER.get(), EnergyCounterScreen::new));
        }
    }
}
