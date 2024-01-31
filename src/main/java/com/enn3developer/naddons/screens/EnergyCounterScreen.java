package com.enn3developer.naddons.screens;

import com.enn3developer.naddons.NAddons;
import com.enn3developer.naddons.menus.EnergyCounterMenu;
import com.enn3developer.naddons.network.NAddonsPacketHandler;
import com.enn3developer.naddons.network.packets.CustomerC2SPacket;
import com.enn3developer.naddons.network.packets.ResetC2SPacket;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

public class EnergyCounterScreen extends AbstractContainerScreen<EnergyCounterMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(NAddons.MODID, "textures/gui/energy_counter.png");
    private final ExtendedButton button;
    private final EditBox editBox;

    public EnergyCounterScreen(EnergyCounterMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);

        this.button = new ExtendedButton(0, 0, 50, 16,
                Component.literal("Reset"),
                button ->
                        NAddonsPacketHandler.sendToServer(
                                new ResetC2SPacket(this.menu.getEnergyCounter().getBlockPos())
                        )
        );

        this.editBox = new EditBox(Minecraft.getInstance().font, 0, 0, 70, 16, Component.empty()) {
            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                if (keyCode == InputConstants.KEY_E) {
                    return true;
                }
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        };
        this.editBox.setValue(this.menu.getEnergyCounter().getCustomer());
        this.editBox.setEditable(true);
        this.editBox.setVisible(true);
        this.editBox.setResponder(string ->
                NAddonsPacketHandler.sendToServer(
                        new CustomerC2SPacket(this.menu.getEnergyCounter().getBlockPos(), string)
                ));
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(this.button);
        this.addRenderableWidget(this.editBox);
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

        float drawX = this.leftPos + ((float) this.imageWidth / 3);
        float drawY = this.topPos + ((float) this.imageHeight / 10);

        String energy = (this.menu.getEnergyCounter() != null ? this.menu.getEnergyCounter().getCountedEU() : 0) + " Wh";
        String power = (this.menu.getEnergyCounter() != null ? this.menu.getEnergyCounter().getPower() : 0) + " kW";

        this.font.draw(poseStack, energy, drawX, drawY, 12);
        this.font.draw(poseStack, power, drawX, drawY + 10, 12);

        this.button.x = (int) (drawX + 60);
        this.button.y = (int) (drawY + 25);

        this.editBox.x = (int) (drawX - 20);
        this.editBox.y = (int) (drawY + 25);
    }
}
