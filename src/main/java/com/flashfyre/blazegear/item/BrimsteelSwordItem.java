package com.flashfyre.blazegear.item;

import java.util.List;

import com.flashfyre.blazegear.BGUtil;
import com.flashfyre.blazegear.registry.BGAttributes;
import com.flashfyre.blazegear.registry.BGItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BrimsteelSwordItem extends SwordItem {
	public BrimsteelSwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("item.blazegear.brimsteel_tool.tooltip").withStyle(ChatFormatting.GRAY));
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return state.ignitedByLava() ? super.getDestroySpeed(stack, state) * 2.0F : super.getDestroySpeed(stack, state);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		if (slot != EquipmentSlot.MAINHAND)
			return super.getAttributeModifiers(slot, stack);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = BGUtil.toolModifierBuilder(slot, stack);
		builder.putAll(super.getAttributeModifiers(slot, stack));
		return builder.build();
	}

	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return 14400;
	}
}
