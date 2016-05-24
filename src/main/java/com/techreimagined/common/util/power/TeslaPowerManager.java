package com.techreimagined.common.util.power;

import com.techreimagined.common.tileentities.TilePoweredBase;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
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
public class TeslaPowerManager implements ITeslaPowerManager {

    AdvancedTeslaContainer container;

    @Override
    public void readFromNBT(NBTTagCompound compound, TilePoweredBase powerAcceptor) {
        this.container = new AdvancedTeslaContainer(compound.getTag("TeslaContainer"), powerAcceptor);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, TilePoweredBase powerAcceptor) {
        compound.setTag("TeslaContainer", this.container.writeNBT());
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing, TilePoweredBase powerAcceptor) {
        if(capability == TeslaCapabilities.CAPABILITY_CONSUMER && powerAcceptor.canAcceptEnergy(facing)){
            this.container.tile = powerAcceptor;
            return (T) this.container;
        } else if (capability == TeslaCapabilities.CAPABILITY_PRODUCER && powerAcceptor.canProvideEnergy(facing)){
            this.container.tile = powerAcceptor;
            return (T) this.container;
        }
        if (capability == TeslaCapabilities.CAPABILITY_HOLDER){
            this.container.tile = powerAcceptor;
            return (T) this.container;
        }
        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing, TilePoweredBase powerAcceptor) {
        if(capability == TeslaCapabilities.CAPABILITY_CONSUMER && powerAcceptor.canAcceptEnergy(facing)){
            return true;
        } else if (capability == TeslaCapabilities.CAPABILITY_PRODUCER && powerAcceptor.canProvideEnergy(facing)){
            return true;
        }
        if (capability == TeslaCapabilities.CAPABILITY_HOLDER)
            return true;
        return false;
    }

    @Override
    public void update(TilePoweredBase powerAcceptor) {
        if (powerAcceptor.canProvideEnergy(null)) {
            //TeslaUtils.distributePowerEqually(powerAcceptor.getWorld(), powerAcceptor.getPos(), (long) powerAcceptor.getMaxOutput(), false);
        }
    }

    @Override
    public void created(TilePoweredBase powerAcceptor) {
        this.container = new AdvancedTeslaContainer(powerAcceptor);
    }

    @Override
    public String getDisplayableTeslaCount(long tesla) {
        return TeslaUtils.getDisplayableTeslaCount(tesla);
    }

    public static ITeslaPowerManager getPowerManager() {
        return new TeslaPowerManager();
    }
}