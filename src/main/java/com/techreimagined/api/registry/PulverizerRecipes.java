package com.techreimagined.api.registry;

import com.google.common.collect.Maps;
import com.techreimagined.common.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import javax.annotation.Nullable;
import java.util.Map;

/* You are free to:
 * 
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 * for any purpose, even commercially.
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 * Under the following terms:

 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 * Notices:
 * 
 * You do not have to comply with the license for elements of the material in the public domain or where your use is permitted by an applicable exception or limitation.
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */
public class PulverizerRecipes {
    private Map<ItemStack, ItemStack> pulverizingList = Maps.<ItemStack, ItemStack>newHashMap();

    /**
     * Adds a smelting recipe, where the input item is an instance of Block.
     */
    public void addPulverizeRecipeForBlock(Block input, ItemStack stack, float experience)
    {
        this.addPulverize(Item.getItemFromBlock(input), stack, experience);
    }

    /**
     * Adds a smelting recipe using an Item as the input item.
     */
    public void addPulverize(Item input, ItemStack stack, float experience)
    {
        this.addPulverizeRecipe(new ItemStack(input, 1, 32767), stack, experience);
    }

    /**
     * Adds a smelting recipe using an ItemStack as the input for the recipe.
     */
    public void addPulverizeRecipe(ItemStack input, ItemStack stack, float experience)
    {
        if (getPulverizeResult(input) != null) { LogHelper.error("Ignored smelting recipe with conflicting input: " + input + " = " + stack); return; }
        this.pulverizingList.put(input, stack);
    }
    /**
     * Returns the smelting result of an item.
     */
    @Nullable
    public ItemStack getPulverizeResult(ItemStack stack)
    {
        for (Map.Entry<ItemStack, ItemStack> entry : this.pulverizingList.entrySet())
        {
            if (this.compareItemStacks(stack, (ItemStack)entry.getKey()))
            {
                return (ItemStack)entry.getValue();
            }
        }

        return null;
    }

    /**
     * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, ItemStack> getpulverizingList()
    {
        return this.pulverizingList;
    }

}
