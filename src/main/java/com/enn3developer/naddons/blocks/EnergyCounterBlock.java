package com.enn3developer.naddons.blocks;

import com.enn3developer.naddons.tiles.EnergyCounterTile;
import ic2.core.block.base.IStateController;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyCounterBlock extends Block implements IStateController<EnergyCounterTile> {
    public EnergyCounterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onStateUpdate(Level level, BlockPos blockPos, BlockState blockState, EnergyCounterTile energyCounterTile) {
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new EnergyCounterTile(blockPos, blockState);
    }
}
