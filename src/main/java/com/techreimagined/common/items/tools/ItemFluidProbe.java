package com.techreimagined.common.items.tools;

import com.techreimagined.TechReimaginedCreativeTabs;
import com.techreimagined.common.items.ItemBase;
import com.techreimagined.common.items.Items;
import com.techreimagined.common.tileentities.generators.TileEntitySolarPanel;
import com.techreimagined.common.util.IProvideRecipe;
import com.techreimagined.common.util.Platform;
import com.techreimagined.common.util.power.TeslaGenerator;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
public class ItemFluidProbe extends ItemBase implements IProvideRecipe {
    public ItemFluidProbe() {
        super("tools/fluidprobe");
        this.setCreativeTab(TechReimaginedCreativeTabs.tabTools);
        this.setInternalName("fluidprobe");
        this.setMaxStackSize(1);
    }

    @Override
    public void RegisterRecipes() {

    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Block block = world.getBlockState(pos).getBlock();
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof IFluidTank) {
            if(((IFluidTank) tile).getFluid()!=null) {
                FluidStack fluid = ((IFluidTank)tile).getFluid();
                player.addChatMessage(new TextComponentString(I18n.format("message.techreimagined.fluidprobe", block.getLocalizedName(), ((IFluidTank) tile).getFluidAmount(),((IFluidTank) tile).getCapacity(), fluid.getLocalizedName())));
            } else {
                player.addChatMessage(new TextComponentString(I18n.format("message.techreimagined.fluidprobe.empty", block.getLocalizedName())));
            }
        }
        return EnumActionResult.FAIL;
    }
    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(String.format(I18n.format("tooltip.techreimagined.fluidprobe.text","§7")));
        super.addInformation(stack, playerIn, tooltip, advanced);
    }
}
