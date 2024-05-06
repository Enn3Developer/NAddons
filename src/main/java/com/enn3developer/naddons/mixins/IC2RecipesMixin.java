package com.enn3developer.naddons.mixins;

import com.enn3developer.naddons.items.Tags;
import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Recipes;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IC2Recipes.class)
public class IC2RecipesMixin {
    @Inject(method = "addComponents", at = @At(value = "TAIL"), remap = false)
    private static void injectAlloyRecipe(IAdvancedCraftingManager recipes, CallbackInfo ci) {
        recipes.addShapedIC2Recipe("n_advanced_alloy", new ItemStack(IC2Items.INGOT_ADVANCED_ALLOY, 2), "III", "BBB", "TTT", 'I', IC2Tags.INGOT_REFINED_IRON, 'B', IC2Tags.INGOT_BRONZE, 'T', Tags.ALUMINUM_TAG);
    }
}
