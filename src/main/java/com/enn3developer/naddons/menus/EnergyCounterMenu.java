package com.enn3developer.naddons.menus;

import com.enn3developer.naddons.NAddons;
import com.enn3developer.naddons.tiles.EnergyCounterTile;
import com.enn3developer.naddons.utils.SlotWithRestrictions;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EnergyCounterMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final EnergyCounterTile energyCounter;

    public EnergyCounterMenu(int containerId, Inventory playerInv, FriendlyByteBuf data) {
        this(containerId, playerInv, new ItemStackHandler(2), Objects.requireNonNull(getBlockEntity(playerInv, data)));
    }

    public EnergyCounterMenu(int containerId, Inventory playerInv, ItemStackHandler inventory, EnergyCounterTile energyCounter) {
        super(NAddons.MENUS.ENERGY_COUNTER.get(), containerId);
        System.out.println("Creating menu");
        this.access = ContainerLevelAccess.create(playerInv.player.level, energyCounter.getBlockPos());
        this.energyCounter = energyCounter;

        this.addSlot(new SlotWithRestrictions(inventory, 0, 8, 18, itemStack -> itemStack.is(IC2Items.TRANSFORMER_UPGRADE)));
        this.addSlot(new SlotWithRestrictions(inventory, 1, 8, 38, itemStack -> itemStack.is(IC2Items.TRANSFORMER_UPGRADE)));

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

    public EnergyCounterTile getEnergyCounter() {
        return this.energyCounter;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);

        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemstack = current.copy();
            if (i < 2) {
                if (!this.moveItemStackTo(current, 2, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(current, 0, 2, false)) {
                return ItemStack.EMPTY;
            }

            if (current.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        if (this.access == null) return true;
        return stillValid(this.access, player, this.energyCounter.getBlockState().getBlock());
    }

    public static @Nullable EnergyCounterTile getBlockEntity(@NotNull Inventory player, @NotNull FriendlyByteBuf data) {
        BlockEntity blockEntity = player.player.level.getBlockEntity(data.readBlockPos());

        if (blockEntity instanceof EnergyCounterTile energyCounter) {
            return energyCounter;
        }

        return null;
    }
}
