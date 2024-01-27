package com.enn3developer.naddons.tiles;

import com.enn3developer.naddons.NAddons;
import com.enn3developer.naddons.blocks.Blocks;
import com.enn3developer.naddons.utils.NRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntities extends NRegister<BlockEntityType<?>> {
    public final RegistryObject<BlockEntityType<EnergyCounterTile>> ENERGY_COUNTER = super.register.register("energy_counter", () -> BlockEntityType.Builder.of(EnergyCounterTile::new, NAddons.BLOCKS.ENERGY_COUNTER.get()).build(null));

    @Override
    protected IForgeRegistry<BlockEntityType<?>> getForgeRegistry() {
        return ForgeRegistries.BLOCK_ENTITY_TYPES;
    }
}
