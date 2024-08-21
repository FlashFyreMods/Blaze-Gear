package com.flashfyre.blazegear.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

public class BrimsteelBlockItem extends BlockItem {

	public BrimsteelBlockItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return 54000;
	}
}
