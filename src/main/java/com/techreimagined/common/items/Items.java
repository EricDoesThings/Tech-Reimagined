package com.techreimagined.common.items;

import com.techreimagined.common.items.debug.ItemTeslaDebug;
<<<<<<< HEAD
import com.techreimagined.common.items.materials.ItemGear;
import com.techreimagined.common.items.ores.ItemOreDust;
import com.techreimagined.common.items.ores.ItemOreIngot;
import com.techreimagined.common.items.ores.ItemOreNugget;
import com.techreimagined.common.items.tools.ItemFluidProbe;
import com.techreimagined.common.items.tools.ItemPowerProbe;
import com.techreimagined.common.items.tools.ItemWrench;
import com.techreimagined.common.items.upgrades.ItemAutomationUpgrade;
import com.techreimagined.common.items.upgrades.ItemCraftingUpgrade;
import com.techreimagined.common.items.upgrades.ItemRedstoneUpgrade;
import com.techreimagined.common.util.RegistrationHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
=======
import com.techreimagined.common.items.ores.ItemOreDust;
import com.techreimagined.common.items.ores.ItemOreIngot;
import com.techreimagined.common.items.ores.ItemOreNugget;
import com.techreimagined.common.items.tools.ItemPowerProbe;
import com.techreimagined.common.items.tools.ItemWrench;
import com.techreimagined.common.items.materials.ItemGear;
import com.techreimagined.common.util.RegistrationHelper;
import net.minecraft.item.*;
>>>>>>> development

public enum Items {
    ITEM_ORE_INGOT(ItemOreIngot.class),
    ITEM_ORE_DUST(ItemOreDust.class),
    ITEM_ORE_NUGGET(ItemOreNugget.class),
    ITEM_MATERIAL_GEAR(ItemGear.class),

    ITEM_TOOL_WRENCH(ItemWrench.class),
    ITEM_TOOL_PROBE(ItemPowerProbe.class),
<<<<<<< HEAD
    ITEM_TOOL_FLUID_PROBE(ItemFluidProbe.class),

    ITEM_TESLA_DEBUG(ItemTeslaDebug.class),



    //Upgrades
    ITEM_UPGRADE_REDSTONE(ItemRedstoneUpgrade.class),
    ITEM_UPGRADE_AUTOMATION(ItemAutomationUpgrade.class),
    ITEM_UPGRADE_CRAFITNG(ItemCraftingUpgrade.class),


=======

    ITEM_TESLA_DEBUG(ItemTeslaDebug.class),

>>>>>>> development
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