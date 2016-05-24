package com.techreimagined.common.util.power;

import com.techreimagined.common.tileentities.TilePoweredBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

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
public class AdvancedTeslaContainer {
    public TilePoweredBase tile;

    public AdvancedTeslaContainer(TilePoweredBase tile) {
        this.tile = tile;
    }

    public AdvancedTeslaContainer(NBTBase nbt, TilePoweredBase tile) {
        this.tile = tile;
        this.readNBT(nbt);
    }

    public long getStoredPower() {
        return (long)tile.getEnergy();
    }

    //Receive
    public long givePower(long tesla, boolean simulated) {
        return (long) (tile.addEnergy(tesla));
    }

    //Take power out
    public long takePower(long tesla,boolean simulated) {
        return (int)(tile.useEnergy(tesla));
    }

    public long getCapacity() {
        return (long)tile.getMaxPower();
    }

    public long getInputRate() {
        return (long)tile.getMaxInput();
    }

    public long getOutputRate() {
        return (long)tile.getMaxOutput();
    }

    public NBTBase writeNBT() {
        NBTTagCompound dataTag = new NBTTagCompound();
        return dataTag;
    }

    public void readNBT(NBTBase nbt) {
    }

    public boolean isInputSide(EnumFacing side) {
        return true;
    }

    public boolean isOutputSide(EnumFacing side) {
        return true;
    }
}
