package com.techreimagined.common.tileentities.debug;

import com.techreimagined.common.util.power.TeslaGenerator;
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
 * You do not have to comply with the l bicense for elements of the material in the public domain or where your use is permitted by an applicable exception or limitation.
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */
public class TeslaDebugContainer implements ITeslaProducer,TeslaGenerator {
    @Override
    public long takePower(long power, boolean simulated) {
        return 0;
    }

    @Override
    public long getGeneration() {
        return 12421;
    }
}
