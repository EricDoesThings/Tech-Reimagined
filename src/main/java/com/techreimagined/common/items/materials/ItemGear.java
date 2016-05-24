package com.techreimagined.common.items.materials;

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
import com.techreimagined.api.EnumOreType;
import com.techreimagined.api.EnumOres;
import com.techreimagined.common.items.ItemBase;
import com.techreimagined.common.items.Items;
import com.techreimagined.common.util.IProvideRecipe;
import com.techreimagined.ModInfo;
import com.techreimagined.TechReimaginedCreativeTabs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class ItemGear extends ItemBase implements IProvideRecipe {
    public ItemGear() {
        super("materials/gear");
        this.setHasSubtypes(true);
        this.setCreativeTab(TechReimaginedCreativeTabs.tabMisc);
        this.setInternalName("material_gear");
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (EnumOres ore : EnumOres.byType(EnumOreType.GEAR)) {
            int i = ore.getMeta();
            subItems.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        String name = super.getUnlocalizedName();
        String oreName = EnumOres.byMeta(itemStack.getItemDamage()).getUnlocalizedName();
        return name + "." + oreName;
    }

    @Override
    public void registerItemRenderer() {
        for (EnumOres ore : EnumOres.byType(EnumOreType.GEAR)) {
            int i = ore.getMeta();
            ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(ModInfo.MOD_ID + ":materials/gear-" + EnumOres.byMeta(i).getUnlocalizedName(), "inventory"));
        }
    }

    @Override
    public void RegisterRecipes() {
        for (EnumOres ore : EnumOres.byType(EnumOreType.GEAR)) {
            int i = ore.getMeta();
            // Normal Gear Recipe
            if (ore.isTypeSet(EnumOreType.INGOT)) {
                GameRegistry.addRecipe(new ShapedOreRecipe(Items.ITEM_MATERIAL_GEAR.getStack(1, i),
                        " x ",
                        "xyx",
                        " x ",
                        'x', "ingot" + EnumOres.byMeta(i).getName(),
                        'y', "ingotIron")
                );
            }

            // Vanilla Material Gears
            if (ore.isTypeSet(EnumOreType.VANILLA)) {
                String outerMaterial = "";
                String innerMaterial = "";

                switch (ore) {
                    case WOOD:
                        outerMaterial = "stickWood";
                        innerMaterial = "plankWood";
                        break;
                    case COBBLESTONE:
                        outerMaterial = "cobblestone";
                        innerMaterial = "plankWood";
                        break;
                }

                GameRegistry.addRecipe(new ShapedOreRecipe(Items.ITEM_MATERIAL_GEAR.getStack(1, i),
                        " x ",
                        "xyx",
                        " x ",
                        'x', outerMaterial,
                        'y', innerMaterial)
                );
            }
        }
    }
}