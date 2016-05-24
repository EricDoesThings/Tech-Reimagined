package com.techreimagined.common.tileentities;

import com.techreimagined.api.EnumPowerTier;
import com.techreimagined.api.features.IEnergyInterfaceTile;
import com.techreimagined.common.util.IListInfoProvider;
import com.techreimagined.common.util.power.TeslaHelper;
import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

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
public class TilePoweredBase extends TileEntityBase implements IEnergyInterfaceTile, IListInfoProvider {

    public int tier;
    private long energy;

    public boolean canAcceptEnergy(EnumFacing side) {
        return true;
    }
    public boolean canProvideEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public void addInfo(List<String> info, boolean isRealTile)
    {
        info.add(TextFormatting.LIGHT_PURPLE + "Energy buffer Size " + TextFormatting.GREEN
                + TeslaHelper.textTranform(getMaxPower()));
        if (getMaxInput() != 0)
        {
            info.add(TextFormatting.LIGHT_PURPLE + "Max Input " + TextFormatting.GREEN
                    + TeslaHelper.textTranform(getMaxInput()));
        }
        if (getMaxOutput() != 0)
        {
            info.add(TextFormatting.LIGHT_PURPLE + "Max Output " + TextFormatting.GREEN
                    + TeslaHelper.textTranform(getMaxOutput()));
        }
        info.add(TextFormatting.LIGHT_PURPLE + "Tier " + TextFormatting.GREEN + getTier().getName());
    }
    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing){
        if(TeslaHelper.isTeslaEnabled()){
            return TeslaHelper.manager.hasCapability(capability, facing, this);
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
        if(TeslaHelper.isTeslaEnabled()){
            return TeslaHelper.manager.getCapability(capability, facing, this);
        }
        return super.getCapability(capability, facing);
    }
    @Override
    public long getEnergy()
    {
        return energy;
    }

    @Override
    public void setEnergy(long energy)
    {
        this.energy = energy;

        if (this.getEnergy() > getMaxPower())
        {
            this.setEnergy(getMaxPower());
        } else if (this.energy < 0)
        {
            this.setEnergy(0);
        }
    }

    @Override
    public long addEnergy(long energy)
    {
        return addEnergy(energy, false);
    }

    @Override
    public long addEnergy(long energy, boolean simulate)
    {
        long energyReceived = Math.min(getMaxPower(), Math.min(this.getMaxPower(), energy));

        if (!simulate)
        {
            setEnergy(getEnergy() + energyReceived);
        }
        return energyReceived;
    }

    @Override
    public boolean canUseEnergy(long input)
    {
        return input <= energy;
    }

    @Override
    public long useEnergy(long energy)
    {
        return useEnergy(energy, false);
    }

    @Override
    public long useEnergy(long extract, boolean simulate)
    {
        if (extract > energy)
        {
            long tempEnergy = energy;
            setEnergy(0);
            return tempEnergy;
        }
        if (!simulate)
        {
            setEnergy(energy - extract);
        }
        return extract;
    }

    @Override
    public boolean canAddEnergy(long energy)
    {
        return this.energy + energy <= getMaxPower();
    }

    @Override
    public EnumPowerTier getTier() {
        return EnumPowerTier.BASIC;
    }

    @Override
    public long getMaxPower() {
        return 0;
    }

    @Override
    public long getMaxOutput() {
        return 0;
    }

    @Override
    public long getMaxInput() {
        return 0;
    }
}
