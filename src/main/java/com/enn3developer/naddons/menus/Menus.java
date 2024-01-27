package com.enn3developer.naddons.menus;

import com.enn3developer.naddons.utils.NRegister;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class Menus extends NRegister<MenuType<?>> {
    public final RegistryObject<MenuType<EnergyCounterMenu>> ENERGY_COUNTER = super.register.register("energy_counter", () -> new MenuType<>(EnergyCounterMenu::new));

    @Override
    protected IForgeRegistry<MenuType<?>> getForgeRegistry() {
        return ForgeRegistries.MENU_TYPES;
    }
}
