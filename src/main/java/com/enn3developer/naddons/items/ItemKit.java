package com.enn3developer.naddons.items;

import com.enn3developer.naddons.NAddons;
import com.zuxelus.energycontrol.api.ItemStackHelper;
import com.zuxelus.energycontrol.crossmod.CrossModLoader;
import com.zuxelus.energycontrol.items.kits.ItemKitMain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ItemKit extends ItemKitMain {
    @Override
    public ItemStack getSensorCard(ItemStack itemStack, Player player, Level level, BlockPos blockPos, Direction direction) {
        BlockEntity te = level.getBlockEntity(blockPos);
        CompoundTag tag = CrossModLoader.getCrossMod(NAddons.MODID).getCardData(te);
        if (tag != null) {
            ItemStack newCard = new ItemStack(Items.ITEM_CARD.get());
            ItemStackHelper.setCoordinates(newCard, blockPos);
            return newCard;
        }
        return ItemStack.EMPTY;
    }
}
