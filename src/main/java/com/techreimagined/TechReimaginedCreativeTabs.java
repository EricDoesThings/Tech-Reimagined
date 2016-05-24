package com.techreimagined;

import com.techreimagined.common.blocks.Blocks;
import com.techreimagined.common.items.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TechReimaginedCreativeTabs {
    public static final CreativeTabs tabPower = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.BLOCK_SOLAR_PANEL.getBlock());
        }

        @Override
        public String getTabLabel() {
            return ModInfo.MOD_ID + ".power";
        }
    }.setBackgroundImageName("techreimagined.png");

    public static final CreativeTabs tabMachines = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.BLOCK_PUMP.getBlock());
        }

        @Override
        public String getTabLabel() {
            return ModInfo.MOD_ID + ".machines";
        }
    }.setBackgroundImageName("techreimagined.png");

    public static final CreativeTabs tabTools = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.ITEM_TOOL_WRENCH.getItem();
        }

        @Override
        public String getTabLabel() {
            return ModInfo.MOD_ID + ".tools";
        }
    }.setBackgroundImageName("techreimagined.png");

    public static final CreativeTabs tabOres = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.BLOCK_ORE.getBlock());
        }

        @Override
        public String getTabLabel() {
            return ModInfo.MOD_ID + ".ores";
        }

    }.setBackgroundImageName("techreimagined.png");

    public static final CreativeTabs tabMisc = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return com.techreimagined.common.items.Items.ITEM_MATERIAL_GEAR.getItem();
        }

        @Override
        public String getTabLabel() {
            return ModInfo.MOD_ID + ".misc";
        }

    }.setBackgroundImageName("techreimagined.png");
}
