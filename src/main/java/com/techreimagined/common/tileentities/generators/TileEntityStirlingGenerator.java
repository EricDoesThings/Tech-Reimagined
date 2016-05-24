package com.techreimagined.common.tileentities.generators;

import com.techreimagined.api.EnumOres;
import com.techreimagined.common.integrations.waila.IWailaBodyMessage;
import com.techreimagined.common.inventory.InternalInventory;
import com.techreimagined.common.inventory.InventoryOperation;
import com.techreimagined.common.items.Items;
import com.techreimagined.common.tileentities.TileEntityBase;
import com.techreimagined.common.tileentities.TileEntityMachineBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

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
public class TileEntityStirlingGenerator extends TileEntityMachineBase implements ITickable, IWailaBodyMessage {
    private InternalInventory inventory = new InternalInventory(this, 100);
    private boolean working = false;
    private boolean upgradeExtraSlots = false;
    private int[] progress = new int[9];
    protected StirlingTeslaContainer container;


    @Override
    public void update() {

    }

    public void initMachineData() {
        super.initMachineData();

        int gearCount = 0;

        NBTTagCompound machineItemData = this.getMachineItemData();
        if (machineItemData != null) {
            for (int i = 0; i < 27; i++) {
                if (machineItemData.hasKey("item_" + i)) {
                    ItemStack item = ItemStack.loadItemStackFromNBT(machineItemData.getCompoundTag("item_" + i));

                    if (ItemStack.areItemsEqual(item, Items.ITEM_MATERIAL_GEAR.getStack(1, EnumOres.IRON.getMeta())))
                        gearCount = item.stackSize;

                    if (ItemStack.areItemsEqual(item, new ItemStack(Blocks.CHEST)))
                        upgradeExtraSlots = true;
                }
            }
        }

    }

    public boolean isWorking() {
        return working;
    }

    @Override
    public void markForUpdate() {
        super.markForUpdate();

        this.markForLightUpdate();
    }
    @Override
    public boolean canBeRotated() {
        return true;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }
    @Override
    public List<String> getWailaBodyToolTip(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currentTip;
    }


    @Override
    public IInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InventoryOperation operation, ItemStack removed, ItemStack added) {

    }

    @Override
    public int[] getAccessibleSlotsBySide(EnumFacing side) {
        return new int[0];
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }
}
