package com.enn3developer.naddons.mixins;

import com.enn3developer.naddons.NAddons;
import com.enn3developer.naddons.NAddonsCrossMod;
import com.zuxelus.energycontrol.crossmod.CrossModBase;
import com.zuxelus.energycontrol.crossmod.CrossModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(CrossModLoader.class)
public abstract class CrossModLoaderMixin {
    @Shadow(remap = false)
    private static void loadCrossMod(String modid, Supplier<? extends CrossModBase> factory) {
    }

    @Inject(method = "init", at = @At(value = "TAIL"), remap = false)
    private static void injectCrossMod(CallbackInfo ci) {
        loadCrossMod(NAddons.MODID, NAddonsCrossMod::new);
    }
}
