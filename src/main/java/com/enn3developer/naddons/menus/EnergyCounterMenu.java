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
        this.addSlot(new Slot(playerInv, 0, 0, 0));
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
