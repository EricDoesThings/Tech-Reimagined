package com.techreimagined.common.tileentities.generators;

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
public class StirlingTeslaContainer implements ITeslaHolder, ITeslaProducer,TeslaGenerator {

    private long storedPower = 0;

    @Override
    public long takePower (long tesla, boolean simulated) {

        final long removedPower = Math.min(this.storedPower, Math.min(this.getGeneration(), tesla));

        if (!simulated)
            this.storedPower -= removedPower;

        return removedPower;
    }

    @Override
    public long getStoredPower () {

        return this.storedPower;
    }

    @Override
    public long getCapacity () {

        return 10000;
    }

    @Override
    public long getGeneration() {
        return 6;
    }

    public void generatePower () {

        this.storedPower += this.getGeneration();

        if (this.storedPower > this.getCapacity())
            this.storedPower = this.getCapacity();
    }
    /**
     * Sets the power of the container. Should only be used internally!
     *
     * @param power
     */
    protected void setPower (long power) {

        this.storedPower = power;
    }
}