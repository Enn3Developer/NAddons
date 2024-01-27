package com.enn3developer.naddons.mixins;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toughasnails.init.ModItems;
import toughasnails.item.DirtyWaterBottleItem;
import toughasnails.item.PurifiedWaterBottleItem;
import toughasnails.util.inventory.ItemGroupTAN;

@Mixin(ModItems.class)
public class ModItemsMixin {
    @Inject(method = "lambda$registerItems$4", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void injectDirtyWaterBottle(CallbackInfoReturnable<Item> cir) {
        cir.setReturnValue(
                new DirtyWaterBottleItem((new Item.Properties()).stacksTo(64).tab(ItemGroupTAN.INSTANCE)));
    }

    @Inject(method = "lambda$registerItems$5", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void injectPurifiedWaterBottle(CallbackInfoReturnable<Item> cir) {
        cir.setReturnValue(
                new PurifiedWaterBottleItem((new Item.Properties()).stacksTo(64).tab(ItemGroupTAN.INSTANCE)));
    }
}
