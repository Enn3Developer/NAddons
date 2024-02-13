package com.enn3developer.naddons.mixins;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {
    @Inject(method = "isAllowedChatCharacter", at = @At(value = "HEAD"), cancellable = true)
    private static void injectColorCode(char pCharacter, CallbackInfoReturnable<Boolean> cir) {
        if (pCharacter == 167) {
            cir.setReturnValue(true);
        }
    }
}
