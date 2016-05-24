package com.techreimagined.common.blocks.cable;

import com.techreimagined.TechReimaginedCreativeTabs;
import com.techreimagined.common.blocks.BlockTileBase;
import com.techreimagined.common.items.Items;
import com.techreimagined.common.tileentities.sample.TileEntitySample;
import com.techreimagined.common.util.TileHelper;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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
public class BlockCable extends BlockTileBase {

    public BlockCable() {
        super(Material.IRON, "misc/cable");
        this.setTileEntity(TileEntity.class);
        this.setCreativeTab(TechReimaginedCreativeTabs.tabPower);
        this.setInternalName("cable");
        this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            final TileEntity tile = worldIn.getTileEntity(pos);

            if(tile.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER,side)) {
                final ITeslaHolder holder = tile.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, side);
                playerIn.addChatMessage(new TextComponentString(holder.getStoredPower() + " / " + holder.getCapacity()));
            }
        } else {
            if(playerIn.isSneaking()) {
                if (heldItem != null && heldItem.equals(Items.ITEM_TOOL_WRENCH.getStack())) {
                    worldIn.destroyBlock(pos, true);
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
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
}
