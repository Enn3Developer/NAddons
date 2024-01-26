package com.enn3developer.naddons.items;

import com.enn3developer.naddons.NAddons;
import com.zuxelus.energycontrol.api.CardState;
import com.zuxelus.energycontrol.api.ICardReader;
import com.zuxelus.energycontrol.api.PanelSetting;
import com.zuxelus.energycontrol.api.PanelString;
import com.zuxelus.energycontrol.crossmod.CrossModLoader;
import com.zuxelus.energycontrol.items.cards.ItemCardMain;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;

public class ItemCard extends ItemCardMain {
    @Override
    public CardState update(Level world, ICardReader reader, int range, BlockPos pos) {
        BlockPos target = reader.getTarget();
        if (target == null)
            return CardState.NO_TARGET;

        BlockEntity te = world.getBlockEntity(target);
        CompoundTag tag = CrossModLoader.getCrossMod(NAddons.MODID).getCardData(te);
        if (tag == null)
            return CardState.NO_TARGET;
        reader.reset();
        reader.copyFrom(tag);
        return CardState.OK;
    }

    @Override
    public List<PanelString> getStringData(Level level, int settings, ICardReader reader, boolean isServer, boolean showLabels) {
        List<PanelString> result = reader.getTitleList();

        if (reader.hasField("n_energy") && (settings & 1) > 0) {
            result.add(new PanelString("msg.naddons.n_energy", reader.getDouble("n_energy"), showLabels));
        }
        if (reader.hasField("n_power") && (settings & 2) > 0) {
            result.add(new PanelString("msg.naddons.n_power", reader.getDouble("n_power"), showLabels));
        }

        return result;
    }

    @Override
    public List<PanelSetting> getSettingsList() {
        List<PanelSetting> settings = new ArrayList<>();

        settings.add(new PanelSetting(I18n.get("msg.naddons.n_energy_settings"), 1));
        settings.add(new PanelSetting(I18n.get("msg.naddons.n_power_settings"), 2));

        return settings;
    }
}
