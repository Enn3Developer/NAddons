package com.enn3developer.naddons.mixins;

import com.enn3developer.naddons.NAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayMixin {
    @Mutable
    @Final
    @Shadow
    static ResourceLocation MOJANG_STUDIOS_LOGO_LOCATION;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void changeLogo(Minecraft pMinecraft, ReloadInstance pReload, Consumer pOnFinish, boolean pFadeIn, CallbackInfo ci) {
        MOJANG_STUDIOS_LOGO_LOCATION = new ResourceLocation(NAddons.MODID, "textures/logo.png");
    }
}
