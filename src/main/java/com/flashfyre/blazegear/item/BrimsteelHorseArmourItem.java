package com.flashfyre.blazegear.item;

import java.util.List;

import com.flashfyre.blazegear.BlazeGear;

import com.flashfyre.blazegear.registry.BGAttributes;
import com.flashfyre.blazegear.registry.BGItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BrimsteelHorseArmourItem extends HorseArmorItem {

	public BrimsteelHorseArmourItem(int armorValue, Properties builder) {
		super(armorValue, new ResourceLocation(BlazeGear.MOD_ID, "textures/models/armor/brimsteel_horse_armour.png"), builder);
	}

	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return 48000;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		if (slot != EquipmentSlot.CHEST)
			return super.getAttributeModifiers(slot, stack);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<Attribute, AttributeModifier>();
		AttributeModifier fireResistanceMod = new AttributeModifier(BGItems.SLOT_MODIFIER_UUIDS[slot.getIndex()], "Armour fire resistance", 0.1F, AttributeModifier.Operation.ADDITION);
		AttributeModifier attackerBurnTimeMod = new AttributeModifier(BGItems.SLOT_MODIFIER_UUIDS[slot.getIndex()], "Armour attacker burn time", 2F, AttributeModifier.Operation.ADDITION);
		builder.put(BGAttributes.FIRE_RESISTANCE.get(), fireResistanceMod);
		builder.put(BGAttributes.ATTACKER_BURN_TIME.get(), attackerBurnTimeMod);
		builder.putAll(super.getAttributeModifiers(slot, stack));
		return builder.build();
	}
}
