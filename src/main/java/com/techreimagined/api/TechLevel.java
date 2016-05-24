package com.techreimagined.api;/* You are free to:
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

import com.techreimagined.ModInfo;
import net.minecraft.util.IStringSerializable;

public enum TechLevel implements IStringSerializable{
    BASIC("basic"),
    ADVANCED("advanced"),
    CREATIVE("creative")

    ;

    private static final TechLevel[] META_LOOKUP = new TechLevel[values().length];

    static {
        for (TechLevel tier : values()) {
            META_LOOKUP[tier.getMeta()] = tier;
        }
    }

    private final String name;

    TechLevel(String name) {
        this.name = name;
    }

    public static TechLevel byMeta(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public static TechLevel[] all() {
        return TechLevel.values();
    }

    public int getMeta() {
        return ordinal();
    }

    public String getName() {
        return this.name;
    }

    public String getUnlocalizedName() {
        return String.format("%s.%s.%s.%s", "label", ModInfo.MOD_ID, "tech_level", this.name);
    }
}
