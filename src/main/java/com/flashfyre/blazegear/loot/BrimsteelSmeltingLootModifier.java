package com.flashfyre.blazegear.loot;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.particles.ParticleTypes;
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
import org.apache.commons.lang3.mutable.MutableBoolean;

public class BrimsteelSmeltingLootModifier extends LootModifier {
	
	public static final Codec<BrimsteelSmeltingLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, BrimsteelSmeltingLootModifier::new));

	protected BrimsteelSmeltingLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext ctx) {
		ObjectArrayList<ItemStack> items = new ObjectArrayList<ItemStack>();
		MutableBoolean smeltedSomething = new MutableBoolean(false);
		generatedLoot.forEach(stack -> {
			items.add(smelt(stack, ctx, smeltedSomething));
		});
		if(smeltedSomething.isTrue() && ctx.getParamOrNull(LootContextParams.ORIGIN) != null) {
			Vec3 origin = ctx.getParam(LootContextParams.ORIGIN);
			for(int i = 0; i < 5; ++i) {
				ctx.getLevel().sendParticles(ParticleTypes.FLAME,
						origin.x() + ctx.getLevel().getRandom().nextDouble() - 0.5D,
						origin.y() + ctx.getLevel().getRandom().nextDouble() - 0.5D,
						origin.z() + ctx.getLevel().getRandom().nextDouble() - 0.5D,
						1, 0.0F, 0.0D, 0.0D, 0.0D);
			}
		}
        return items;
	}
	
	public static ItemStack smelt(ItemStack stack, LootContext ctx, MutableBoolean smeltedSomething) {
		if(!stack.isEmpty()) {
			Optional<SmeltingRecipe> opt = ctx.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), ctx.getLevel());
			if(opt.isPresent()) {
				SmeltingRecipe recipe = opt.get();
				if(ctx.getParamOrNull(LootContextParams.ORIGIN) != null) {
					splitAndSpawnExperience(ctx.getLevel(), ctx.getParam(LootContextParams.ORIGIN), 1, recipe.getExperience());
				}
				ItemStack result = opt.get().getResultItem(ctx.getLevel().registryAccess());
				if(!result.isEmpty()) {
					ItemStack newStack = result.copy();
					newStack.setCount(stack.getCount() * result.getCount());
					smeltedSomething.setTrue();
					return newStack;
				}
			}
		}
		return stack;
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
