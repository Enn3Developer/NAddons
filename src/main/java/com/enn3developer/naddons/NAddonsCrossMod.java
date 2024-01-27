package com.enn3developer.naddons;

import com.enn3developer.naddons.tiles.EnergyCounterTile;
import com.zuxelus.energycontrol.crossmod.CrossModBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NAddonsCrossMod extends CrossModBase {
    @Override
    public CompoundTag getCardData(BlockEntity te) {
        if (!(te instanceof EnergyCounterTile energyCounter)) {
            return null;
        }
        CompoundTag tag = new CompoundTag();
        tag.putDouble("n_energy", energyCounter.getCountedEU());
        tag.putDouble("n_power", energyCounter.getAddedPerTick());
        return tag;
    }
}
