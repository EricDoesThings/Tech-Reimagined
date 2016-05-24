package com.techreimagined.common.items;

import com.google.common.collect.Multimap;
import com.techreimagined.TechReimaginedCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

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
public abstract class ItemBaseTool extends ItemBase {
    protected float efficiencyOnProperMaterial;
    /**
     * Damage versus entities.
     */
    protected float damageVsEntity;
    protected float attackSpeed;
    /**
     * The material this tool is made from.
     */
    protected Item.ToolMaterial toolMaterial;
    private Set<Block> effectiveBlocks;
    /*===================================== FORGE START =================================*/
    private String toolClass;

    protected ItemBaseTool(float p_i46745_1_, float p_i46745_2_, Item.ToolMaterial p_i46745_3_, Set<Block> p_i46745_4_, String texture) {
        super(texture);
        this.efficiencyOnProperMaterial = 4.0F;
        this.toolMaterial = p_i46745_3_;
        this.effectiveBlocks = p_i46745_4_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i46745_3_.getMaxUses());
        this.efficiencyOnProperMaterial = p_i46745_3_.getEfficiencyOnProperMaterial();
        this.damageVsEntity = p_i46745_1_ + p_i46745_3_.getDamageVsEntity();
        this.attackSpeed = p_i46745_2_;
        this.setCreativeTab(TechReimaginedCreativeTabs.tabTools);
    }

    protected ItemBaseTool(Item.ToolMaterial p_i46746_1_, Set<Block> p_i46746_2_, String texture) {
        this(0.0F, 0.0F, p_i46746_1_, p_i46746_2_, texture);
    }

    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        for (String type : getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state))
                return efficiencyOnProperMaterial;
        }
        return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : 1.0F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(2, attacker);
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving) {
        if ((double) blockIn.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(1, entityLiving);
        }

        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    public Item.ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        ItemStack mat = this.toolMaterial.getRepairItemStack();
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double) this.damageVsEntity, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) this.attackSpeed, 0));
        }

        return multimap;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        int level = super.getHarvestLevel(stack, toolClass);
        if (level == -1 && toolClass != null && toolClass.equals(this.toolClass)) {
            return this.toolMaterial.getHarvestLevel();
        } else {
            return level;
        }
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
    }
    /*===================================== FORGE END =================================*/
}