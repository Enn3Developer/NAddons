package com.enn3developer.naddons.network.packets;

import com.enn3developer.naddons.tiles.EnergyCounterTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResetC2SPacket {
    private final BlockPos pos;

    public ResetC2SPacket(BlockPos pos) {
        this.pos = pos;
    }

    public ResetC2SPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ServerLevel level = player.getLevel();
                BlockEntity blockEntity = level.getBlockEntity(this.pos);
                if (blockEntity instanceof EnergyCounterTile energyCounter) {
                    energyCounter.reset();
                }
            }
        });
        return true;
    }
}
