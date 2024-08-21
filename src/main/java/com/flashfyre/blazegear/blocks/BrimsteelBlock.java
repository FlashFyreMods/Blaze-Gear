package com.flashfyre.blazegear.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BrimsteelBlock extends Block {

	public BrimsteelBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
			entity.hurt(level.damageSources().hotFloor(), 1.0F);
		}
		super.stepOn(level, pos, state, entity);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void randomTick(BlockState stateIn, ServerLevel level, BlockPos pos, RandomSource source) {
		double x = (double)pos.getX() + level.random.nextDouble();
        double y = (double)pos.getY() + level.random.nextDouble();
        double z = (double)pos.getZ() + level.random.nextDouble();
        level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
	}
}
