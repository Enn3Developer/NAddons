package com.enn3developer.naddons.items;

import com.enn3developer.naddons.NAddons;
import com.enn3developer.naddons.utils.NRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class Items extends NRegister<Item> {
    private final static CreativeModeTab N_ADDONS_TAB = new CreativeModeTab("nAddons") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(NAddons.ITEMS.ENERGY_COUNTER.get());
        }
    };

    public final RegistryObject<Item> ENERGY_COUNTER = super.register.register("energy_counter", () -> new BlockItem(NAddons.BLOCKS.ENERGY_COUNTER.get(), new Item.Properties().tab(N_ADDONS_TAB)));
    public final RegistryObject<Item> WIRELESS_RECEIVER = super.register.register("wireless_receiver", () -> new BlockItem(NAddons.BLOCKS.WIRELESS_RECEIVER.get(), new Item.Properties().tab(N_ADDONS_TAB)));
    public final RegistryObject<ItemCard> ITEM_CARD = super.register.register("item_card", ItemCard::new);
    public final RegistryObject<ItemKit> ITEM_KIT = super.register.register("item_kit", ItemKit::new);

    @Override
    protected IForgeRegistry<Item> getForgeRegistry() {
        return ForgeRegistries.ITEMS;
    }
}
