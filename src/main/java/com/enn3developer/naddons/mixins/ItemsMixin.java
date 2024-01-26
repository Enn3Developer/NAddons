package com.enn3developer.naddons.mixins;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class ItemsMixin {
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/PotionItem;"), remap = false)
    private static PotionItem potionStack(Item.Properties pProperties) {
        return new PotionItem(pProperties.stacksTo(64));
    }
}
