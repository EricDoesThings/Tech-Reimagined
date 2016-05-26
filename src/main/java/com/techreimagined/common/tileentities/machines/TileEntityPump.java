package com.techreimagined.common.tileentities.machines;

import com.techreimagined.common.tileentities.TileEntityBase;
<<<<<<< HEAD
import com.techreimagined.common.util.fluid.Tank;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
=======
import com.techreimagined.common.util.fluid.FluidUtils;
import com.techreimagined.common.util.fluid.Tank;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
>>>>>>> development

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
<<<<<<< HEAD
public class TileEntityPump extends TileEntityBase implements ITickable, IFluidHandler {

    private final PumpTeslaContainer container;
    protected FluidStack fluid;
    protected int capacity;
    private FluidStack lastBeforeUpdate = null;
    public static int BUCKET_VOLUME = FluidContainerRegistry.BUCKET_VOLUME;
    public static int MAX_LIQUID = BUCKET_VOLUME * 16;
    public Tank tank = new Tank("pumpTank",MAX_LIQUID,this);
=======
public class TileEntityPump extends TileEntityBase implements ITickable {

    private final PumpTeslaContainer container;
    public Tank tank = new Tank("TilePump", 10000, this);
>>>>>>> development

    public TileEntityPump() {
        this.container = new PumpTeslaContainer();
    }

    @Override
    public void update () {
        super.update();
<<<<<<< HEAD
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 10 == 0 && tank.isFull() && tank.getCapacity() - tank.getFluidAmount() >= BUCKET_VOLUME && container.canUseEnergy(50)){
            FluidStack fluidStack = drainBlock(worldObj, pos.down(), false);
            if(fluidStack != null && container.getStoredPower()>=50) {
                    fill(EnumFacing.UP,drainBlock(worldObj,pos.down(),true),true);
                    container.setPower(container.getStoredPower()-50);
=======
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 10 == 0 && !tank.isFull() && tank.getCapacity() - tank.getFluidAmount() >= 1000 && container.canUseEnergy(50)){
            FluidStack fluidStack = FluidUtils.drainBlock(worldObj,pos.down(),false);
            if(fluidStack!=null) {
                tank.fill(FluidUtils.drainBlock(worldObj,pos,true),true);
                container.useEnergy(50,true);
>>>>>>> development
            }
            tank.compareAndUpdate();
        }
    }

    @Override
    public void readFromNBT (NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.container.setPower(compound.getLong("StoredPower"));
    }

    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound) {

        compound.setLong("StoredPower", this.container.getStoredPower());
        return super.writeToNBT(compound);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {

        if (facing != EnumFacing.DOWN && (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER))
            return (T) this.container;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {

        if (facing != EnumFacing.DOWN && (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER))
            return true;

        return super.hasCapability(capability, facing);
    }

<<<<<<< HEAD
    // IFluidHandler implementation.

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


    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
        if(!tank.isFull()&&tank.getCapacity()-tank.getFluidAmount()>=resource.amount&&from!=EnumFacing.DOWN){
            return tank.fill(resource,doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        FluidStack stack = tank.drain(resource.amount,false);
        if(stack.equals(resource)&&from!=EnumFacing.DOWN&&stack.amount>=resource.amount)
            return tank.drain(resource.amount, doDrain);
        else
            return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        if(from!=EnumFacing.DOWN)
            return tank.drain(maxDrain, doDrain);
        else
            return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        if(from!=EnumFacing.DOWN)
            return true;
        else
            return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[] { tank.getInfo() };
    }
=======

>>>>>>> development
}