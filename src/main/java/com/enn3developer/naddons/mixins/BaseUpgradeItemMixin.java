package com.enn3developer.naddons.mixins;

import ic2.core.item.base.IC2Item;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseUpgradeItem.class)
public class BaseUpgradeItemMixin extends IC2Item {
    public BaseUpgradeItemMixin(String itemName) {
        super(itemName);
    }

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At(value = "RETURN"), remap = false)
    public void setStackSize(String itemName, CallbackInfo ci) {
        super.maxStackSize = 4;
    }
}
