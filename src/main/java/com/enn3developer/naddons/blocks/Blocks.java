package com.enn3developer.naddons.blocks;

import com.enn3developer.naddons.utils.NRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class Blocks extends NRegister<Block> {
    public final RegistryObject<Block> ENERGY_COUNTER = super.register.register("energy_counter", () -> new EnergyCounterBlock(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.IRON_BLOCK).noOcclusion()));
    public final RegistryObject<Block> WIRELESS_RECEIVER = super.register.register("wireless_receiver", () -> new WirelessReceiverBlock(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OBSERVER)));

    @Override
    protected IForgeRegistry<Block> getForgeRegistry() {
        return ForgeRegistries.BLOCKS;
    }
}
