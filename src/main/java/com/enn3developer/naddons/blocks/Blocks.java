package com.enn3developer.naddons.blocks;

import com.enn3developer.naddons.NAddons;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Blocks {
    private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, NAddons.MODID);
    public static final RegistryObject<Block> ENERGY_COUNTER = REGISTER.register("energy_counter", () -> new EnergyCounterBlock(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.IRON_BLOCK).noOcclusion()));

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
