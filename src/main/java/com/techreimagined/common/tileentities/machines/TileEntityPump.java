package com.techreimagined.common.tileentities.machines;

import com.techreimagined.common.tileentities.TileEntityBase;
import com.techreimagined.common.util.fluid.FluidUtils;
import com.techreimagined.common.util.fluid.Tank;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

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
public class TileEntityPump extends TileEntityBase implements ITickable {

    private final PumpTeslaContainer container;
    public Tank tank = new Tank("TilePump", 10000, this);

    public TileEntityPump() {
        this.container = new PumpTeslaContainer();
    }

    @Override
    public void update () {
        super.update();
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 10 == 0 && !tank.isFull() && tank.getCapacity() - tank.getFluidAmount() >= 1000 && container.canUseEnergy(50)){
            FluidStack fluidStack = FluidUtils.drainBlock(worldObj,pos.down(),false);
            if(fluidStack!=null) {
                tank.fill(FluidUtils.drainBlock(worldObj,pos,true),true);
                container.useEnergy(50,true);
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


}