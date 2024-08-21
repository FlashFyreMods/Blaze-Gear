package com.flashfyre.blazegear.items;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BrimsteelAxeItem extends AxeItem {
	
	public BrimsteelAxeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level LevelIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("item.blazegear.brimsteel_tool.tooltip").withStyle(ChatFormatting.DARK_RED));
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return state.ignitedByLava() ? super.getDestroySpeed(stack, state) * 2.0F : super.getDestroySpeed(stack, state);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		int fireDuration = 4;
		int level = EnchantmentHelper.getFireAspect(attacker);
		if(level > 0) {
			fireDuration += (level * 4);
		}
		target.setSecondsOnFire(fireDuration);
		return super.hurtEnemy(stack, target, attacker);
	}
	
	@Override
	public int getBurnTime(ItemStack stack, RecipeType<?> recipeType) {
		return 22800;
	}
}
