package com.enn3developer.naddons.tiles;

import com.enn3developer.naddons.NAddons;
import com.enn3developer.naddons.blocks.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NAddons.MODID);
    public static final RegistryObject<BlockEntityType<EnergyCounterTile>> ENERGY_COUNTER = REGISTER.register("energy_counter", () -> BlockEntityType.Builder.of(EnergyCounterTile::new, Blocks.ENERGY_COUNTER.get()).build(null));

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
