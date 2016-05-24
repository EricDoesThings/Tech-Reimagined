package com.techreimagined.common.tileentities;

import com.techreimagined.common.items.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
public abstract class TileEntityMachineBase extends TileEntityInventoryBase {
    private boolean comparatorEnabled = false;
    private boolean sidedEnabled = false;
    private boolean craftingEnabled = false;
    private boolean redstoneEnabled = false;

    @Override
    public void initMachineData() {
        NBTTagCompound machineItemData = this.getMachineItemData();
        if (machineItemData != null) {
            for (int i = 0; i < 27; i++) {
                if (machineItemData.hasKey("item_" + i)) {
                    ItemStack item = ItemStack.loadItemStackFromNBT(machineItemData.getCompoundTag("item_" + i));

                    if (ItemStack.areItemsEqual(item, new ItemStack(Items.ITEM_UPGRADE_REDSTONE.getItem())))
                        comparatorEnabled = true;

                    if (ItemStack.areItemsEqual(item, new ItemStack(Items.ITEM_UPGRADE_AUTOMATION.getItem())))
                        sidedEnabled = true;

                    if (ItemStack.areItemsEqual(item, new ItemStack(Items.ITEM_UPGRADE_CRAFITNG.getItem())))
                        craftingEnabled = true;

                    if (ItemStack.areItemsEqual(item, new ItemStack(Items.ITEM_UPGRADE_REDSTONE.getItem())))
                        redstoneEnabled = true;
                }
            }
        }

        if (machineItemData == null) {
            // Load Default Details for the machine...
            comparatorEnabled = false;
            sidedEnabled = false;
            craftingEnabled = false;
            redstoneEnabled = false;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        comparatorEnabled = nbtTagCompound.getBoolean("comparatorEnabled");
        sidedEnabled = nbtTagCompound.getBoolean("sidedEnabled");
        craftingEnabled = nbtTagCompound.getBoolean("craftingEnabled");
        redstoneEnabled = nbtTagCompound.getBoolean("redstoneEnabled");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setBoolean("comparatorEnabled", comparatorEnabled);
        nbtTagCompound.setBoolean("sidedEnabled", sidedEnabled);
        nbtTagCompound.setBoolean("craftingEnabled", craftingEnabled);
        nbtTagCompound.setBoolean("redstoneEnabled", redstoneEnabled);
        return nbtTagCompound;
    }


    public boolean isComparatorEnabled() {
        return comparatorEnabled;
    }

    public boolean isSidedEnabled() {
        return sidedEnabled;
    }

    public boolean isCraftingEnabled() {
        return craftingEnabled;
    }

    public boolean isRedstoneEnabled() {
        return redstoneEnabled;
    }
}
