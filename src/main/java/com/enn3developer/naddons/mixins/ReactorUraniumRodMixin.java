package com.enn3developer.naddons.mixins;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.ReactorUraniumRod;
import ic2.core.item.reactor.base.IUraniumRod;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ReactorUraniumRod.class)
public class ReactorUraniumRodMixin {
    @Shadow
    @Final
    int rodCount;
    @Shadow
    @Final
    IUraniumRod uranium;

    @Redirect(method = "processChamber", at = @At(value = "INVOKE", target = "Lic2/api/reactor/IReactor;setStackInReactor(IILnet/minecraft/world/item/ItemStack;)V"), remap = false)
    public void dropDepleted(IReactor reactor, int x, int y, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            reactor.setStackInReactor(x, y, this.uranium.createNearDepletedRod(this.rodCount));
        } else {
            reactor.setStackInReactor(x, y, itemStack);
        }
    }
}
