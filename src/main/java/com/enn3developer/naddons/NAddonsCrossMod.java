package com.enn3developer.naddons;

import com.enn3developer.naddons.tiles.EnergyCounterTile;
import com.zuxelus.energycontrol.crossmod.CrossModBase;
import com.zuxelus.energycontrol.utils.DataHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NAddonsCrossMod extends CrossModBase {
    @Override
    public CompoundTag getEnergyData(BlockEntity te) {
        if (!(te instanceof EnergyCounterTile energyCounter)) {
            return null;
        }
        
        CompoundTag tag = new CompoundTag();
        tag.putString(DataHelper.EUTYPE, "EU");
        tag.putDouble(DataHelper.ENERGY, energyCounter.getCountedEU());
        return tag;

    }
}
