package com.flashfyre.blazegear.item;

import com.flashfyre.blazegear.registry.BGAttributes;
import com.flashfyre.blazegear.registry.BGItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class NethersteelArmourItem extends ArmorItem {

    public NethersteelArmourItem(ArmorMaterial materialIn, Type head, Properties builderIn) {
        super(materialIn, head, builderIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != this.type.getSlot())
            return super.getAttributeModifiers(slot, stack);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<Attribute, AttributeModifier>();
        AttributeModifier fireResistanceMod = new AttributeModifier(BGItems.SLOT_MODIFIER_UUIDS[slot.getIndex()], "Armour fire resistance", 0.05F, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackerBurnTimeMod = new AttributeModifier(BGItems.SLOT_MODIFIER_UUIDS[slot.getIndex()], "Armour attacker burn time", 1F, AttributeModifier.Operation.ADDITION);
        builder.put(BGAttributes.FIRE_RESISTANCE.get(), fireResistanceMod);
        builder.put(BGAttributes.ATTACKER_BURN_TIME.get(), attackerBurnTimeMod);
        builder.putAll(super.getAttributeModifiers(slot, stack));
        return builder.build();
    }
}