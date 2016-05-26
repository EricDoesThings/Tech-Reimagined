package com.techreimagined.common.tileentities.generators;

import com.techreimagined.common.tileentities.TileEntityBase;
import com.techreimagined.common.util.TileHelper;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.capabilities.Capability;

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
public class TileEntitySolarPanel extends TileEntityBase implements ITickable {

    private final SolarTeslaContainer container;

    public TileEntitySolarPanel() {
        this.container = new SolarTeslaContainer(this);
    }

    @Override
    public void update () {
        if (this.hasWorldObj() && !this.worldObj.provider.getHasNoSky())
            this.container.generatePower();

        for(int i=0; i < 4; i++) {
            TileEntity tile;
            EnumFacing side;
            if(i==0) {
                tile = this.worldObj.getTileEntity(this.pos.south());
                side = EnumFacing.SOUTH;
            } else if(i==1) {
                tile = this.worldObj.getTileEntity(this.pos.west());
                side = EnumFacing.WEST;
            } else if(i==2) {
                tile = this.worldObj.getTileEntity(this.pos.east());
                side = EnumFacing.EAST;
            } else if(i==3) {
                tile = this.worldObj.getTileEntity(this.pos.north());
                side = EnumFacing.NORTH;
            } else {
                tile = this.worldObj.getTileEntity(this.pos.down());
                side = EnumFacing.DOWN;
            }
            if(tile!=null&&tile.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER,side)&&container.getStoredPower()>=10){
                tile.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER,side).givePower(container.takePower(10,false),false);
            }
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

        if (facing != EnumFacing.UP && (capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_HOLDER))
            return (T) this.container;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {

        if (facing != EnumFacing.UP && (capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_HOLDER))
            return true;

        return super.hasCapability(capability, facing);
    }

}