package com.enn3developer.naddons.menus;

import com.enn3developer.naddons.utils.NRegister;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class Menus extends NRegister<MenuType<?>> {
    @Override
    protected IForgeRegistry<MenuType<?>> getForgeRegistry() {
        return ForgeRegistries.MENU_TYPES;
    }
}
