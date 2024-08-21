package com.flashfyre.blazegear.loot;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class BrimsteelSmeltingLootModifier extends LootModifier {
	
	public static final Codec<BrimsteelSmeltingLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, BrimsteelSmeltingLootModifier::new));

	protected BrimsteelSmeltingLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		ObjectArrayList<ItemStack> items = new ObjectArrayList<ItemStack>();
        generatedLoot.forEach((stack) -> 
        	items.add(smelt(stack, context))     	
        );
        return items;
	}
	
	public static ItemStack smelt(ItemStack stack, LootContext context) {
		getAndSpawnXP(stack, context);
		if(stack.isEmpty()) {
			return stack;
		} else {
			Optional<SmeltingRecipe> opt = context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), context.getLevel());
	        if(opt.isPresent()) {
	        	ItemStack result = opt.get().getResultItem(context.getLevel().registryAccess());
	        	if(!result.isEmpty()) {
	        		ItemStack newStack = result.copy();
	        		newStack.setCount(stack.getCount() * result.getCount());
	        		return newStack;
	        	}
	        }
	        return stack;
		}		
	}
	
	public static void getAndSpawnXP(ItemStack stack, LootContext context) {
		Optional<SmeltingRecipe> xp = context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), context.getLevel());
		xp.ifPresent(recipe -> 
			splitAndSpawnExperience(context.getLevel(), context.getParamOrNull(LootContextParams.ORIGIN), 1, recipe.getExperience()));		
	}
	
	public static void splitAndSpawnExperience(Level world, Vec3 pos, int craftedAmount, float experience) {
		int i = Mth.floor((float)craftedAmount * experience);
		float f = Mth.frac((float)craftedAmount * experience);
		if (f != 0.0F && Math.random() < (double)f) {
			++i;
		}
		while(i > 0) {
			int j = ExperienceOrb.getExperienceValue(i);
			i -= j;
			world.addFreshEntity(new ExperienceOrb(world, pos.x, pos.y, pos.z, j));
		}
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
}
