package com.techreimagined.common.util.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

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
public class FluidUtils {
    public static FluidStack drainBlock(World world, BlockPos pos, boolean doDrain){
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
        if(fluid!=null&&FluidRegistry.isFluidRegistered(fluid)){
            if(block instanceof IFluidBlock){
                IFluidBlock fluidBlock = (IFluidBlock) block;
                if(!fluidBlock.canDrain(world,pos))
                    return null;
                return fluidBlock.drain(world,pos,doDrain);
            } else {
                int level = state.getValue(BlockLiquid.LEVEL);
                if(level != 0)
                    return null;
                if(doDrain)
                    world.setBlockToAir(pos);
                return new FluidStack(fluid,1000);
            }
        } else {
            return null;
        }
    }
}
