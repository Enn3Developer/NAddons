package com.enn3developer.naddons.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tiles.IEnergyStorage;
import ic2.core.block.base.tiles.BaseTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EnergyCounterTile extends BaseTileEntity implements IEnergyStorage, IEnergySink, IEnergySource {
    private int storedEU;
    private long countedEU;
    private int maxEU;
    private int maxOut;
    private int tier;
    private boolean addedToEnergyNet;

    public EnergyCounterTile(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
        this.tier = 1;
        this.maxEU = 8192;
        this.countedEU = 0;
        this.storedEU = 0;
        this.maxOut = 32;
    }

    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnergyNet) {
            this.addedToEnergyNet = true;
            EnergyNet.INSTANCE.addTile(this);
        }

    }

    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnergyNet) {
            this.addedToEnergyNet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }

        super.onUnloaded(chunk);
    }

    @Override
    public BlockEntityType<?> createType() {
        return BlockEntities.ENERGY_COUNTER.get();
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        this.countedEU = tag.getLong("N_CNTD");
        this.storedEU = tag.getInt("N_STRD");
        this.maxEU = tag.getInt("N_MAX");
        this.tier = tag.getInt("N_TIER");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putLong("N_CNTD", this.countedEU);
        tag.putInt("N_STRD", this.storedEU);
        tag.putInt("N_MAX", this.maxEU);
        tag.putInt("N_TIER", this.tier);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();

        tag.putLong("N_CNTD", this.countedEU);
        tag.putInt("N_STRD", this.storedEU);
        tag.putInt("N_MAX", this.maxEU);
        tag.putInt("N_TIER", this.tier);

        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);

        this.countedEU = tag.getLong("N_CNTD");
        this.storedEU = tag.getInt("N_STRD");
        this.maxEU = tag.getInt("N_MAX");
        this.tier = tag.getInt("N_TIER");
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public int addEnergy(int power) {
        int added = Math.min(this.maxEU - this.storedEU, power);
        if (added > 0) {
            this.storedEU += added;
            this.setChanged();
        }
        return added;
    }

    @Override
    public int drawEnergy(int power) {
        int removed = Math.min(power, this.storedEU);
        if (removed > 0) {
            this.storedEU -= removed;
            this.countedEU += removed;
            this.setChanged();
        }
        return removed;
    }

    @Override
    public int getStoredEU() {
        return this.storedEU;
    }

    @Override
    public int getMaxEU() {
        return this.maxEU;
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    public int getSinkTier() {
        return this.tier;
    }

    @Override
    public int getRequestedEnergy() {
        return this.maxEU - this.storedEU;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount <= this.maxEU && amount > 0) {
            int added = Math.min(amount, this.maxEU - this.storedEU);
            if (added > 0) {
                this.storedEU += added;
                this.setChanged();
            }

            return amount - added;
        } else {
            return 0;
        }
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter iEnergyEmitter, Direction direction) {
        return direction.getAxisDirection().getName().contains("negative");
    }

    @Override
    public int getSourceTier() {
        return this.tier;
    }

    @Override
    public int getMaxEnergyOutput() {
        return this.maxOut;
    }

    @Override
    public int getProvidedEnergy() {
        return this.storedEU >= this.maxOut ? this.maxOut : 0;
    }

    @Override
    public void consumeEnergy(int i) {
        this.drawEnergy(i);
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
        return direction.getAxisDirection().getName().contains("positive");
    }
}
