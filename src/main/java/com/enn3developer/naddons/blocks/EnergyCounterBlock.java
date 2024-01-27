package com.enn3developer.naddons.blocks;

import com.enn3developer.naddons.items.Items;
import com.enn3developer.naddons.tiles.BlockEntities;
import com.enn3developer.naddons.tiles.EnergyCounterTile;
import ic2.api.blocks.IWrenchable;
import ic2.core.block.base.IStateController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnergyCounterBlock extends Block implements IStateController<EnergyCounterTile>, IWrenchable {
    public EnergyCounterBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        if (direction.get2DDataValue() != -1) {
            return this.defaultBlockState().setValue(BlockStateProperties.FACING, direction);
        } else {
            return this.defaultBlockState();
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        if (entityType == BlockEntities.ENERGY_COUNTER.get()) {
            return EnergyCounterTile::tick;
        }
        return null;
    }

    @Override
    public void onStateUpdate(Level level, BlockPos blockPos, BlockState blockState, EnergyCounterTile energyCounterTile) {
        switch (energyCounterTile.getAcceptFacing()) {
            case "east" -> blockState = blockState.setValue(BlockStateProperties.FACING, Direction.NORTH);
            case "west" -> blockState = blockState.setValue(BlockStateProperties.FACING, Direction.SOUTH);
            case "north" -> blockState = blockState.setValue(BlockStateProperties.FACING, Direction.WEST);
            case "south" -> blockState = blockState.setValue(BlockStateProperties.FACING, Direction.EAST);
        }
        if (level != null) {
            level.setBlockAndUpdate(blockPos, blockState);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new EnergyCounterTile(blockPos, blockState);
    }

    @Override
    public Direction getFacing(BlockState blockState, Level level, BlockPos blockPos) {
        return blockState.getValue(BlockStateProperties.FACING);
    }

    @Override
    public boolean canSetFacing(BlockState blockState, Level level, BlockPos blockPos, Player player, Direction direction) {
        return blockState.getValue(BlockStateProperties.FACING) != direction && direction.get2DDataValue() != -1;
    }

    @Override
    public boolean setFacing(BlockState blockState, Level level, BlockPos blockPos, Player player, Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof EnergyCounterTile energyCounter) {
            if (!energyCounter.isSimulating()) {
                return false;
            }
            energyCounter.setFacing(direction);
            return true;
        }
        return false;
    }

    @Override
    public boolean doSpecialAction(BlockState blockState, Level level, BlockPos blockPos, Direction direction, Player player, Vec3 vec3) {
        return false;
    }

    @Override
    public AABB hasSpecialAction(BlockState blockState, Level level, BlockPos blockPos, Direction direction, Player player, Vec3 vec3) {
        return null;
    }

    @Override
    public boolean canRemoveBlock(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        return true;
    }

    @Override
    public double getDropRate(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        return 1.0;
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        List<ItemStack> drops = new ArrayList<>(1);
        drops.add(new ItemStack(Items.ENERGY_COUNTER.get()));
        return drops;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof EnergyCounterTile energyCounter) {
            if (!energyCounter.isSimulating()) {
                return InteractionResult.PASS;
            }
            pPlayer.sendSystemMessage(Component.literal("Energy Counter: " + energyCounter.getCountedEU() + " EU"));
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
