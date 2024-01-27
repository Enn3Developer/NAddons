package com.enn3developer.naddons.utils;

import com.enn3developer.naddons.NAddons;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class NRegister<T> {
    protected final DeferredRegister<T> register = DeferredRegister.create(this.getForgeRegistry(), NAddons.MODID);

    protected abstract IForgeRegistry<T> getForgeRegistry();

    public void register(IEventBus modEventBus) {
        register.register(modEventBus);
    }
}
