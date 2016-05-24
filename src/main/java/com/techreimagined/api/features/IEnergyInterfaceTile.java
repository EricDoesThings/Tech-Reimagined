package com.techreimagined.api.features;

import com.techreimagined.api.EnumPowerTier;
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
public interface IEnergyInterfaceTile
{

    /**
     * @return Amount of energy in the tile
     */
    public long getEnergy();

    /**
     * Sets the energy in the tile
     *
     * @param energy
     *            the amount of energy to set.
     */
    public void setEnergy(long energy);

    /**
     * Gets the max stored energy in the tile
     *
     * @return The max energy
     */
    public long getMaxPower();

    /**
     * @param energy
     *            amount of energy to add to the tile
     * @return will return true if can fit all
     */
    public boolean canAddEnergy(long energy);

    /**
     * Will try add add the full amount of energy.
     *
     * @param energy
     *            amount to add
     * @return The amount of energy that was added.
     */
    public long addEnergy(long energy);

    /**
     * Will try add add the full amount of energy, if simulate is true it wont
     * add the energy
     *
     * @param energy
     *            amount to add
     * @param simulate
     *            set to true to simulate not perform the action.
     * @return The amount of energy that was added.
     */
    public long addEnergy(long energy, boolean simulate);

    /**
     * Returns true if it can use the full amount of energy
     *
     * @param energy
     *            amount of energy to use from the tile.
     * @return if all the energy can be used.
     */
    public boolean canUseEnergy(long energy);

    /**
     * Will try and use the full amount of energy
     *
     * @param energy
     *            energy to use
     * @return the amount of energy used
     */
    public long useEnergy(long energy);

    /**
     * Will try and use the full amount of energy, if simulate is true it wont
     * add the energy
     *
     * @param energy
     *            energy to use
     * @param simulate
     *            set to true to simulate not perform the action.
     * @return the amount of energy used
     */
    public long useEnergy(long energy, boolean simulate);

    /**
     * @param direction
     *            The direction to insert energy into
     * @return if the tile can accept energy from the direction
     */
    public boolean canAcceptEnergy(EnumFacing direction);

    /**
     * @param direction
     *            The direction to provide energy from
     * @return true if the tile can provide energy to that direction
     */
    public boolean canProvideEnergy(EnumFacing direction);

    /**
     * Gets the max output, set to -1 if you don't want the tile to provide
     * energy
     *
     * @return the max amount of energy outputted per tick.
     */
    public long getMaxOutput();

    /**
     * Return -1 if you don't want to accept power ever.
     *
     * @return The max amount of energy that can be added to the tile in one
     *         tick.
     */
    public long getMaxInput();

    /**
     *
     * Gets the teir, used for machine explosions
     *
     * @return the teir
     */
    public EnumPowerTier getTier();

}
