package com.enn3developer.naddons.utils;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotWithRestrictions extends SlotItemHandler {
    public SlotWithRestrictions(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
}
