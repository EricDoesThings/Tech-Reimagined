package com.techreimagined.common.tileentities.storage;

import com.techreimagined.api.EnumPowerTier;
import com.techreimagined.common.tileentities.TileEntityBase;
import com.techreimagined.common.util.IListInfoProvider;
import com.techreimagined.common.util.fluid.Tank;
import com.techreimagined.common.util.power.TeslaHelper;
import net.darkhax.tesla.api.BaseTeslaContainer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;

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
public class TileEntityTank extends TileEntityBase implements IFluidHandler,IListInfoProvider {

    public Tank tank = new Tank("TilePump", 10000, this);

    @Override
    public void update() {
        super.update();
    }
    @Override
    public void addInfo(List<String> info, boolean isRealTile) {
        info.add(TextFormatting.LIGHT_PURPLE +tank.getFluid().getLocalizedName()+": " + TextFormatting.GREEN
                + tank.getFluidAmount()+"mb");
    }
    private BaseTeslaContainer container;

    public TileEntityTank(){
    }

    @Override
    public void readFromNBT (NBTTagCompound compound) {

        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    // IFluidHandler
    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill)
    {
        int fill = tank.fill(resource, doFill);
        tank.compareAndUpdate();
        return fill;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
    {
        FluidStack drain = tank.drain(resource.amount, doDrain);
        tank.compareAndUpdate();
        return drain;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
    {
        FluidStack drain = tank.drain(maxDrain, doDrain);
        tank.compareAndUpdate();
        return drain;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid)
    {
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid)
    {
        return tank.getFluid() == null || tank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from)
    {
        return new FluidTankInfo[] { tank.getInfo() };
    }
}
