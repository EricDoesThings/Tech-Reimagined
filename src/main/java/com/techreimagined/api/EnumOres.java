package com.techreimagined.api;

import com.techreimagined.api.features.DimensionType;
import net.minecraft.util.IStringSerializable;
import java.util.ArrayList;
import java.util.Arrays;
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
public enum EnumOres implements IStringSerializable {

    // Vanilla Ores
    IRON("Iron", 0, EnumOreType.NUGGET, EnumOreType.DUST, EnumOreType.VANILLA, EnumOreType.GEAR, EnumOreType.FLUID),
    GOLD("Gold", 1, EnumOreType.DUST, EnumOreType.VANILLA, EnumOreType.GEAR, EnumOreType.FLUID),
    DIAMOND("Diamond", 2, EnumOreType.NUGGET, EnumOreType.VANILLA),

    // Base Ores
    COPPER("Copper", 3, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.ORE, EnumOreType.GEAR, EnumOreType.FLUID),
    TIN("Tin", 4, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.ORE, EnumOreType.GEAR, EnumOreType.FLUID),
    SILVER("Silver", 5, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.ORE, EnumOreType.GEAR, EnumOreType.FLUID),
    LEAD("Lead", 6, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.ORE, EnumOreType.GEAR, EnumOreType.FLUID),
    NICKEL("Nickel", 7, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.ORE, EnumOreType.GEAR, EnumOreType.FLUID),
    //Nether
    CHROMITE("Chromite", 8,DimensionType.NETHER, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.ORE, EnumOreType.GEAR, EnumOreType.FLUID),
    MAGNETITE("Magnetite", 9,DimensionType.NETHER, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.ORE, EnumOreType.GEAR, EnumOreType.FLUID),

    // Alloys
    TITANIUM("Titanium", 10, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.GEAR, EnumOreType.FLUID),
    BRONZE("Bronze", 11, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.GEAR, EnumOreType.FLUID),
    ELECTRUM("Electrum", 12, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.GEAR, EnumOreType.FLUID),
    STEEL("Steel", 13, EnumOreType.BLOCK, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.DUST, EnumOreType.GEAR, EnumOreType.FLUID),

    // Misc Gears
    WOOD("Wood", 14, EnumOreType.GEAR, EnumOreType.VANILLA),
    COBBLESTONE("Stone", 15, EnumOreType.GEAR, EnumOreType.VANILLA),;

    private static final EnumOres[] META_LOOKUP = new EnumOres[values().length];

    static {
        for (EnumOres ore : values()) {
            if (ore.getMeta() < 0 || ore.getMeta() >= values().length) {
                //Dont use this index, Invalid Meta ID!!!!
            } else {
                //Yes use this index, It be gooood!!
                META_LOOKUP[ore.getMeta()] = ore;
            }
        }
    }

    private final String name;
    private final int meta;
    private final DimensionType dimension;
    private final EnumOreType[] enumOresTypeList;

    EnumOres(String name,int meta, EnumOreType... type) {
        this(name,meta,DimensionType.OVERWORLD,type);
    }

    EnumOres(String name, int meta, DimensionType d, EnumOreType... oreTypes) {
        this.name = name;
        this.meta = meta;
        this.enumOresTypeList = oreTypes;
        if(this.isTypeSet(EnumOreType.ORE)){
            this.dimension=d;
        } else {
            this.dimension=null;
        }
    }

    public static EnumOres byMeta(int meta) {
        if (meta < 0 || meta >= values().length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public static List<EnumOres> byType(EnumOreType type) {
        List<EnumOres> result = new ArrayList<EnumOres>();

        for (EnumOres ore : values()) {
            if (ore.isTypeSet(type)) {
                result.add(ore);
            }
        }

        return result;
    }

    public int getMeta() {
        return this.meta;
    }
    public DimensionType getDimension() {
        return this.dimension;
    }

    public String getUnlocalizedName() {
        return this.name.toLowerCase();
    }

    public String getName() {
        return this.name.toLowerCase();
    }

    public String getOreName() {
        return this.name;
    }

    public String toString() {
        return getName();
    }

    public boolean isTypeSet(EnumOreType enumOreType) {
        return Arrays.asList(enumOresTypeList).contains(enumOreType);
    }

}