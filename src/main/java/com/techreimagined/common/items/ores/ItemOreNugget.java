package com.techreimagined.common.items.ores;

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
import com.techreimagined.ModInfo;
import com.techreimagined.TechReimaginedCreativeTabs;
import com.techreimagined.api.EnumOreType;
import com.techreimagined.api.EnumOres;
import com.techreimagined.common.items.ItemBase;
import com.techreimagined.common.items.Items;
import com.techreimagined.common.util.IProvideRecipe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

public class ItemOreNugget extends ItemBase implements IProvideRecipe {
    public ItemOreNugget() {
        super("ores/nugget");
        this.setHasSubtypes(true);
        this.setCreativeTab(TechReimaginedCreativeTabs.tabOres);
        this.setInternalName("ore_nugget");
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (EnumOres ore : EnumOres.values()) {
            int i = ore.getMeta();
            if (ore.isTypeSet(EnumOreType.NUGGET)) {
                subItems.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = super.getUnlocalizedName();
        String oreName = EnumOres.byMeta(stack.getItemDamage()).getUnlocalizedName();
        return name + "." + oreName;
    }

    @Override
    public void registerItemRenderer() {
        for (EnumOres ore : EnumOres.values()) {
            int i = ore.getMeta();
            if (ore.isTypeSet(EnumOreType.NUGGET)) {
                ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(ModInfo.MOD_ID + ":ores/nugget-" + ore.getUnlocalizedName(), "inventory"));
            }
        }
    }

    @Override
    public void RegisterRecipes() {
        for (EnumOres ore : EnumOres.values()) {
            int i = ore.getMeta();
            // Ingot -> 9x Nugget
            if (ore.isTypeSet(EnumOreType.INGOT) && ore.isTypeSet(EnumOreType.NUGGET)) {
                GameRegistry.addRecipe(new ShapelessOreRecipe(Items.ITEM_ORE_NUGGET.getStack(9, i), "ingot" + ore.getName()));
            }

            // Iron Ingot -> 9x Iron Nuggets
            if (ore == EnumOres.IRON) {
                GameRegistry.addRecipe(new ShapelessOreRecipe(Items.ITEM_ORE_NUGGET.getStack(9, i), "ingotIron"));
            }

            // Diamond -> 9x Diamond Nuggets
            if (ore == EnumOres.DIAMOND) {
                GameRegistry.addRecipe(new ShapelessOreRecipe(Items.ITEM_ORE_NUGGET.getStack(9, i), "gemDiamond"));
            }
        }
    }
}