package com.techreimagined.common.tileentities.machines;

import com.techreimagined.common.util.power.ITeslaUtils;
import com.techreimagined.common.util.power.TeslaGenerator;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;

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
public class PumpTeslaContainer implements ITeslaHolder, ITeslaConsumer,ITeslaUtils {

    /**
     * The power stored by the pump.
     */
    private long storedPower = 0;

    @Override
    public boolean canUseEnergy(long input) {
        return input <= storedPower;
    }

    @Override
    public long useEnergy(long input,boolean simulated) {
        if(canUseEnergy(input)) {
            if((storedPower+input)>getCapacity()) {
                if(!simulated)
                    setPower(getCapacity());
                return input;
            } else {
                if(!simulated)
                    setPower(storedPower + input);
                return input;
            }
        } else {
            return 0;
        }
    }

    @Override
    public long givePower(long power, boolean simulated) {
<<<<<<< HEAD
        long addedPower;
        if(power+storedPower>getCapacity())
            addedPower=0;
        else
            addedPower=power;
=======

        final long addedPower = Math.min(this.storedPower, power);
>>>>>>> development
        if(!simulated)
            this.storedPower += addedPower;
        return addedPower;
    }

    @Override
    public long getStoredPower () {
        return this.storedPower;
    }

    @Override
    public long getCapacity () {

        return 10000;
    }

    /**
     * Sets the power of the container. Should only be used in debugging!
     *
     * @param power
     */
    protected void setPower (long power) {

        this.storedPower = power;
    }
}