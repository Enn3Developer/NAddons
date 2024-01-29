package com.enn3developer.naddons.screens;

import com.enn3developer.naddons.NAddons;
import com.enn3developer.naddons.menus.EnergyCounterMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EnergyCounterScreen extends AbstractContainerScreen<EnergyCounterMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(NAddons.MODID, "textures/gui/energy_counter.png");

    public EnergyCounterScreen(EnergyCounterMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        System.out.println("Creating screen");
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        float drawX = this.leftPos + ((float) this.imageWidth / 10) + 30;
        float drawY = this.topPos + ((float) this.imageHeight / 10) - 5;
        String energy = (this.menu.getEnergyCounter() != null ? this.menu.getEnergyCounter().getCountedEU() : 0) + " Wh";
        String power = (this.menu.getEnergyCounter() != null ? this.menu.getEnergyCounter().getPower() : 0) + " kW";
        this.font.draw(poseStack, energy, drawX, drawY, 12);
        this.font.draw(poseStack, power, drawX, drawY + 10, 12);
        this.addRenderableWidget(new PlainTextButton((int) drawX, (int) (drawY + 30), 20, 20, Component.literal("Reset"), button -> {

        }, this.font));
    }
}
