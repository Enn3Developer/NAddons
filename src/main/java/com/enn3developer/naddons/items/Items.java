package com.enn3developer.naddons.items;

import com.enn3developer.naddons.NAddons;
import com.enn3developer.naddons.blocks.Blocks;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class Items {
    private final static CreativeModeTab N_ADDONS_TAB = new CreativeModeTab("nAddons") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ENERGY_COUNTER.get());
        }
    };

    private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, NAddons.MODID);
    public static final RegistryObject<Item> ENERGY_COUNTER = REGISTER.register("energy_counter", () -> new BlockItem(Blocks.ENERGY_COUNTER.get(), new Item.Properties().tab(N_ADDONS_TAB)));
    public static final RegistryObject<ItemCard> ITEM_CARD = REGISTER.register("item_card", ItemCard::new);
    public static final RegistryObject<ItemKit> ITEM_KIT = REGISTER.register("item_kit", ItemKit::new);

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
