package com.enn3developer.naddons.mixins;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import toughasnails.init.ModItems;

@Mixin(ModItems.class)
public class ModItemsMixin {
    @Redirect(method = "lambda$registerItems$4", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;"), remap = false)
    private static Item.Properties dirtyWaterBottle(Item.Properties instance, int pMaxStackSize) {
        return instance.stacksTo(64);
    }

    @Redirect(method = "lambda$registerItems$5", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;"), remap = false)
    private static Item.Properties purifiedWaterBottle(Item.Properties instance, int pMaxStackSize) {
        return instance.stacksTo(64);
    }
}
