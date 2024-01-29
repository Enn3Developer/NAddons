package com.enn3developer.naddons.network.packets;

import com.enn3developer.naddons.tiles.EnergyCounterTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CustomerC2SPacket {
    private final BlockPos pos;
    private final String customer;

    public CustomerC2SPacket(BlockPos pos, String customer) {
        this.pos = pos;
        this.customer = customer;
    }

    public CustomerC2SPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.customer = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeUtf(this.customer);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ServerLevel level = player.getLevel();
                BlockEntity blockEntity = level.getBlockEntity(this.pos);
                if (blockEntity instanceof EnergyCounterTile energyCounter && (player.getDisplayName().getString().equals(energyCounter.getOwnerName()) && player.hasPermissions(4))) {
                    energyCounter.setCustomer(this.customer);
                } else {
                    player.sendSystemMessage(Component.translatable("msg.naddons.no_permission"));
                }
            }
        });
        return true;
    }
}
