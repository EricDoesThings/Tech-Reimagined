package com.techreimagined.proxy;

import com.techreimagined.TechReimagined;
import com.techreimagined.api.EnumOreType;
import com.techreimagined.api.EnumOres;
import com.techreimagined.client.gui.GuiHandler;
import com.techreimagined.common.blocks.Blocks;
import com.techreimagined.common.config.Config;
import com.techreimagined.common.items.Items;
import com.techreimagined.common.util.IProvideEvent;
import com.techreimagined.common.util.IProvideRecipe;
import com.techreimagined.common.util.IProvideSmelting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerBlocks() {
        Blocks.registerBlocks();
    }

    @Override
    public void registerOreDict() {
        for (EnumOres ore : EnumOres.values()){
            int meta = ore.getMeta();
            String oreName = ore.getOreName();

            if(ore.isTypeSet(EnumOreType.ORE))
                OreDictionary.registerOre("ore" + oreName, Blocks.BLOCK_ORE.getStack(1, meta));
            if(ore.isTypeSet(EnumOreType.BLOCK))
                OreDictionary.registerOre("block" + oreName, Blocks.BLOCK_ORE_BLOCK.getStack(1, meta));
            if(ore.isTypeSet(EnumOreType.INGOT))
                OreDictionary.registerOre("ingot" + oreName, Items.ITEM_ORE_INGOT.getStack(1, meta));
            if(ore.isTypeSet(EnumOreType.DUST))
                OreDictionary.registerOre("ore" + oreName, Items.ITEM_ORE_DUST.getStack(1, meta));
            if(ore.isTypeSet(EnumOreType.NUGGET))
                OreDictionary.registerOre("ore" + oreName, Items.ITEM_ORE_NUGGET.getStack(1, meta));
        }
    }

    @Override
    public void registerItems() {
        Items.registerItems();
    }

    @Override
    public void registerFurnaceRecipes() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideSmelting)
                ((IProvideSmelting) item.getItem()).RegisterSmelting();
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideSmelting)
                ((IProvideSmelting) block.getBlock()).RegisterSmelting();
        }
    }

    @Override
    public void registerRecipes() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideRecipe)
                ((IProvideRecipe) item.getItem()).RegisterRecipes();
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideRecipe)
                ((IProvideRecipe) block.getBlock()).RegisterRecipes();
        }
    }

    @Override
    public void registerEvents() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideEvent)
                MinecraftForge.EVENT_BUS.register(item.getItem());
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideEvent)
                MinecraftForge.EVENT_BUS.register(block.getBlock());
        }
    }

    @Override
    public void registerGUIs() {
        NetworkRegistry.INSTANCE.registerGuiHandler(TechReimagined.instance, new GuiHandler());
    }

    @Override
    public void registerRenderers() {
        /** Client Side Only **/
    }

    @Override
    public void registerConfiguration(File configFile) {
        TechReimagined.configuration = Config.initConfig(configFile);
    }
}
