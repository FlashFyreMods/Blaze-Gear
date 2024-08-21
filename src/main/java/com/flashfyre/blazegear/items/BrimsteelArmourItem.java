package com.flashfyre.blazegear.items;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BrimsteelArmourItem extends ArmorItem {
	
	public BrimsteelArmourItem(ArmorMaterial materialIn, Type head, Properties builderIn) {
		super(materialIn, head, builderIn);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("item.blazegear.brimsteel_armour.tooltip").withStyle(ChatFormatting.DARK_RED));
	}
	
	@Override
	public int getBurnTime(ItemStack stack, RecipeType<?> recipeType) {
		switch(this.getType().getSlot()) {
		case HEAD:
			return 30000;
		case CHEST:
			return 48000;
		case LEGS:
			return 42000;
		case FEET:
			return 24000;
		default:
			return 0;			
		}
	}
}
