package com.techreimagined.common.blocks.generators;

import com.techreimagined.TechReimaginedCreativeTabs;
import com.techreimagined.api.TechLevel;
import com.techreimagined.common.blocks.BlockTechBase;
import com.techreimagined.common.tileentities.generators.TileEntityStirlingGenerator;
import net.minecraft.block.state.BlockStateBase;
import com.techreimagined.common.blocks.BlockTileBase;
import com.techreimagined.common.tileentities.generators.TileEntitySolarPanel;
import com.techreimagined.common.util.TileHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

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
public class BlockStirlingGenerator extends BlockTechBase {
    private static final PropertyBool WORKING = PropertyBool.create("working");

    public BlockStirlingGenerator() {
        super(Material.IRON, "generator/stirling", TechLevel.BASIC, TechLevel.ADVANCED, TechLevel.CREATIVE);
        this.setDefaultState(blockState.getBaseState().withProperty(TECHLEVEL,TechLevel.BASIC).withProperty(FACING, EnumFacing.NORTH));
        this.setTileEntity(TileEntityStirlingGenerator.class);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        IBlockState blockState = getActualState(getDefaultState(), world, pos);
        return (boolean) blockState.getValue(WORKING) ? 15 : 0;
    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TECHLEVEL, TechLevel.byMeta(meta));
    }


    //TODO: See if this is correct
    /*@Override
    public BlockStateContainer getBlockState() {
        int meta = getMetaFromState(this.getBlockState().getBaseState());
        return this.getDefaultState().withProperty(TECHLEVEL, TechLevel.byMeta(meta)).getBlock().getBlockState();
    }*/

    @Override
    public int getMetaFromState(IBlockState state) {
        TechLevel tier = (TechLevel) state.getValue(TECHLEVEL);
        return tier.getMeta();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntityStirlingGenerator tileEntity = TileHelper.getTileEntity(worldIn, pos, TileEntityStirlingGenerator.class);
        if (tileEntity != null && tileEntity.canBeRotated()) {
            return state.withProperty(FACING, tileEntity.getForward()).withProperty(WORKING, tileEntity.isWorking());
        }
        return state.withProperty(FACING, EnumFacing.NORTH).withProperty(WORKING, false);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TECHLEVEL, FACING, WORKING);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }
}

