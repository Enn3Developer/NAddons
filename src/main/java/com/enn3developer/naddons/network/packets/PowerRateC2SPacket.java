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

public class PowerRateC2SPacket {
    private final BlockPos pos;
    private final String powerRate;

    public PowerRateC2SPacket(BlockPos pos, String powerRate) {
        this.pos = pos;
        this.powerRate = powerRate;
    }

    public PowerRateC2SPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.powerRate = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeUtf(this.powerRate);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ServerLevel level = player.getLevel();
                BlockEntity blockEntity = level.getBlockEntity(this.pos);
                if (blockEntity instanceof EnergyCounterTile energyCounter && (player.getDisplayName().getString().equals(energyCounter.getOwnerName()) && player.hasPermissions(4))) {
                    energyCounter.setPowerRate(this.powerRate);
                } else {
                    player.sendSystemMessage(Component.translatable("msg.naddons.no_permission"));
                }
            }
        });
        return true;
    }
}
