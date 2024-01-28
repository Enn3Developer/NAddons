package com.enn3developer.naddons.menus;

import com.enn3developer.naddons.NAddons;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EnergyCounterMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;

    public EnergyCounterMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, ContainerLevelAccess.NULL);
    }

    public EnergyCounterMenu(int containerId, Inventory playerInv, ContainerLevelAccess access) {
        super(NAddons.MENUS.ENERGY_COUNTER.get(), containerId);
        this.access = access;

        final int slotSizePlus2 = 18;
        final int startX = 8;
        final int startY = 84;
        final int hotbarY = 142;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInv, column + row * 9 + 9, startX + column * slotSizePlus2,
                        startY + row * slotSizePlus2));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInv, column, startX + column * slotSizePlus2, hotbarY));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, NAddons.BLOCKS.ENERGY_COUNTER.get());
    }
}
