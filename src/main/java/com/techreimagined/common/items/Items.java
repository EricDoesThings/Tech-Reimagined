package com.techreimagined.common.items;

import com.techreimagined.common.items.debug.ItemTeslaDebug;
import com.techreimagined.common.items.ores.ItemOreDust;
import com.techreimagined.common.items.ores.ItemOreIngot;
import com.techreimagined.common.items.ores.ItemOreNugget;
import com.techreimagined.common.items.tools.ItemPowerProbe;
import com.techreimagined.common.items.tools.ItemWrench;
import com.techreimagined.common.items.materials.ItemGear;
import com.techreimagined.common.util.RegistrationHelper;
import net.minecraft.item.*;

public enum Items {
    ITEM_ORE_INGOT(ItemOreIngot.class),
    ITEM_ORE_DUST(ItemOreDust.class),
    ITEM_ORE_NUGGET(ItemOreNugget.class),
    ITEM_MATERIAL_GEAR(ItemGear.class),

    ITEM_TOOL_WRENCH(ItemWrench.class),
    ITEM_TOOL_PROBE(ItemPowerProbe.class),

    ITEM_TESLA_DEBUG(ItemTeslaDebug.class),

    ;

    private final Class<? extends Item> itemClass;
    private Item item;

    Items(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
    }

    public static void registerItems() {
        for (Items i : Items.values()) {
            i.registerItem();
        }
    }

    public ItemStack getStack() {
        return new ItemStack(item);
    }

    public ItemStack getStack(int size) {
        return new ItemStack(item, size);
    }

    public ItemStack getStack(int size, int damage) {
        return new ItemStack(item, size, damage);
    }

    public Item getItem() {
        return this.item;
    }

    private void registerItem() {
        item = RegistrationHelper.registerItem(itemClass);
    }
}