package com.enn3developer.naddons.blocks;

import com.enn3developer.naddons.NAddons;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WireMeshBlock extends IronBarsBlock {
    public WireMeshBlock(Properties p_54198_) {
        super(p_54198_);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState blockState, LootContext.@NotNull Builder lootContext) {
        List<ItemStack> drops = new ArrayList<>(1);
        drops.add(new ItemStack(NAddons.ITEMS.WIRE_MESH.get()));
        return drops;
    }
}
