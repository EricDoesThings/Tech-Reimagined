package com.techreimagined.common.blocks.sample;

import com.techreimagined.common.blocks.BlockTileBase;
import com.techreimagined.common.tileentities.sample.TileEntitySample;
import com.techreimagined.common.util.IProvideRecipe;
import com.techreimagined.common.util.TileHelper;
import com.techreimagined.TechReimaginedCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockSample extends BlockTileBase implements IProvideRecipe {

    public BlockSample() {
        super(Material.ROCK, "sample/sampleblock");
        this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setTileEntity(TileEntitySample.class);
        this.setCreativeTab(TechReimaginedCreativeTabs.tabPower);
        this.setInternalName("sample_block");
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntitySample tileEntity = TileHelper.getTileEntity(worldIn, pos, TileEntitySample.class);
        if (tileEntity != null) {
            return state.withProperty(FACING, tileEntity.getForward());
        }
        return state.withProperty(FACING, EnumFacing.NORTH);
    }

    @Override
    public void RegisterRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this),
                " x ",
                "x x",
                " x ",
                'x', "logWood"
        ));
    }
}
