package com.flashfyre.blazegear.items;

import java.util.List;

import com.flashfyre.blazegear.BlazeGear;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("item.blazegear.brimsteel_horse_armour.tooltip").withStyle(ChatFormatting.DARK_RED));
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return 48000;
	}
}
