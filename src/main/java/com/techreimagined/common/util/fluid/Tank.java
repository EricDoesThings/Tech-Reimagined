package com.techreimagined.common.util.fluid;

import com.techreimagined.common.network.PacketHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

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
public class Tank extends FluidTank
{

    private final String name;

    private FluidStack lastBeforeUpdate = null;

    public Tank(String name, int capacity, TileEntity tile)
    {
        super(capacity);
        this.name = name;
        this.tile = tile;
    }

    public boolean isEmpty()
    {
        return getFluid() == null || getFluid().amount <= 0;
    }

    public boolean isFull()
    {
        return getFluid() != null && getFluid().amount >= getCapacity();
    }

    public Fluid getFluidType()
    {
        return getFluid() != null ? getFluid().getFluid() : null;
    }

    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        NBTTagCompound tankData = new NBTTagCompound();
        super.writeToNBT(tankData);
        nbt.setTag(name, tankData);
        return nbt;
    }

    public void setFluidAmount(int amount){
        if(fluid != null){
            fluid.amount = amount;
        }
    }

    @Override
    public final FluidTank readFromNBT(NBTTagCompound nbt)
    {
        if (nbt.hasKey(name))
        {
            // allow to read empty tanks
            setFluid(null);

            NBTTagCompound tankData = nbt.getCompoundTag(name);
            super.readFromNBT(tankData);
        }
        return this;
    }

    public void compareAndUpdate()
    {
        if (tile == null || tile.getWorld().isRemote)
        {
            return;
        }
        FluidStack current = this.getFluid();
        if (current != null)
        {
            if (lastBeforeUpdate != null)
            {
                if (Math.abs(current.amount - lastBeforeUpdate.amount) >= 500)
                {
                    PacketHandler.sendPacketToAllPlayers(tile.getUpdatePacket(), tile.getWorld());
                    lastBeforeUpdate = current.copy();
                } else if (lastBeforeUpdate.amount < this.getCapacity() && current.amount == this.getCapacity()
                        || lastBeforeUpdate.amount == this.getCapacity() && current.amount < this.getCapacity())
                {
                    PacketHandler.sendPacketToAllPlayers(tile.getUpdatePacket(), tile.getWorld());
                    lastBeforeUpdate = current.copy();
                }
            } else
            {
                PacketHandler.sendPacketToAllPlayers(tile.getUpdatePacket(), tile.getWorld());
                lastBeforeUpdate = current.copy();
            }
        } else if (lastBeforeUpdate != null)
        {
            PacketHandler.sendPacketToAllPlayers(tile.getUpdatePacket(), tile.getWorld());
            lastBeforeUpdate = null;
        }
    }

}
