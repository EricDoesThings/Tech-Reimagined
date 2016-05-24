package com.techreimagined.common.blocks;

import com.techreimagined.ModInfo;
import com.techreimagined.api.TechLevel;
import com.techreimagined.common.util.IBlockRenderer;
import com.google.common.collect.Maps;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
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
public abstract class BlockTechBase extends BlockTileBase implements IBlockRenderer {
    protected static final PropertyEnum TECHLEVEL = PropertyEnum.create("tech", TechLevel.class);
    private TechLevel[] techLevels;

    public BlockTechBase(Material material, String resourcePath, TechLevel... techLevels) {
        super(material, resourcePath);
        this.techLevels = techLevels;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockRenderer() {
        final String resourcePath = String.format("%s:%s-", ModInfo.MOD_ID, this.resourcePath);
        final String badPath = String.format("%s:badblock", ModInfo.MOD_ID);

        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                Map<IProperty, Comparable> blockStates = Maps.newLinkedHashMap(state.getProperties());

                if (!Arrays.asList(techLevels).contains(blockStates.get(TECHLEVEL)))
                    return new ModelResourceLocation(badPath, "");

                if (blockStates.containsKey(TECHLEVEL))
                    blockStates.remove(TECHLEVEL);

                return new ModelResourceLocation(resourcePath + ((TechLevel) state.getValue(TECHLEVEL)).getName());
            }
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemRenderer() {
        final String resourcePath = String.format("%s:%s-", ModInfo.MOD_ID, this.resourcePath);
        final String badPath = String.format("%s:badblock", ModInfo.MOD_ID);

        for (TechLevel techLevel : TechLevel.values()) {
            if (!Arrays.asList(techLevels).contains(techLevel)) {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), techLevel.getMeta(), new ModelResourceLocation(badPath, "inventory"));
            } else {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), techLevel.getMeta(), new ModelResourceLocation(resourcePath + techLevel.getName(), "inventory"));
            }
        }
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (techLevels.length == 0)
            super.getSubBlocks(itemIn, tab, list);

        for (TechLevel techLevel : techLevels) {
            list.add(new ItemStack(this, 1, techLevel.getMeta()));
        }
    }

}