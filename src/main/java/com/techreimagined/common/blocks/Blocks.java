package com.techreimagined.common.blocks;

import com.techreimagined.common.blocks.generators.BlockSolarPanel;
import com.techreimagined.common.blocks.machines.BlockPump;
import com.techreimagined.common.blocks.ores.BlockOre;
import com.techreimagined.common.blocks.ores.BlockOreBlock;
import com.techreimagined.common.items.generators.ItemSolarPanel;
import com.techreimagined.common.items.machines.ItemPump;
import com.techreimagined.common.items.ores.ItemOre;
import com.techreimagined.common.items.ores.ItemOreBlock;
import com.techreimagined.common.util.RegistrationHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public enum Blocks {
    BLOCK_ORE(BlockOre.class, ItemOre.class),
    BLOCK_ORE_BLOCK(BlockOreBlock.class, ItemOreBlock.class),

    BLOCK_PUMP(BlockPump.class, ItemPump.class),

    BLOCK_SOLAR_PANEL(BlockSolarPanel.class, ItemSolarPanel.class),


    ;

    private final Class<? extends BlockBase> blockClass;
    private final Class<? extends ItemBlock> itemBlockClass;
    private Block block;

    Blocks(Class<? extends BlockBase> blockClass) {
        this(blockClass, ItemBlock.class);
    }

    Blocks(Class<? extends BlockBase> blockClass, Class<? extends ItemBlock> itemBlockClass) {
        this.blockClass = blockClass;
        this.itemBlockClass = itemBlockClass;
    }

    public static void registerBlocks() {
        for (Blocks block : Blocks.values()) {
            block.registerBlock();
        }
    }

    public ItemStack getStack() {
        return new ItemStack(block);
    }

    public ItemStack getStack(int size) {
        return new ItemStack(block, size);
    }

    public ItemStack getStack(int size, int meta) {
        return new ItemStack(block, size, meta);
    }

    public Block getBlock() {
        return this.block;
    }

    private void registerBlock() {
        block = RegistrationHelper.registerBlock(blockClass, itemBlockClass);
    }
}