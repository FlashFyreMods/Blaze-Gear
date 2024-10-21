package com.flashfyre.blazegear;

import com.flashfyre.blazegear.client.BrimsteelParticlePacket;

import com.flashfyre.blazegear.registry.BGAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid=BlazeGear.MOD_ID)
public class EventHandler {
	
	@SubscribeEvent
	public static void spawnSmokeParticles(LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		Level level = entity.getCommandSenderWorld();
		if (level instanceof ServerLevel) {
			if(BGUtil.isEntityWearingFullBrimsteel(entity)) {
				double xzSpread = 0.5D;
				if(entity instanceof Player) {
					xzSpread = 0.6D;
				}
				// We send a packet to clients tracking the chunk telling them to spawn particles at this entity's position
				BlazeGear.SIMPLE_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> entity.getCommandSenderWorld().getChunkAt(entity.blockPosition())), new BrimsteelParticlePacket(entity.getId(), entity.getRandomX(xzSpread), entity.getRandomY(), entity.getRandomZ(xzSpread)));		
			}			
		}		
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		if(event.getSource().is(DamageTypeTags.IS_FIRE)) {
			if(entity.getAttribute(BGAttributes.FIRE_RESISTANCE.get()) != null && entity.getAttributeValue(BGAttributes.FIRE_RESISTANCE.get()) > 0) {
				event.setAmount(event.getAmount() * (1.0F - (float)entity.getAttributeValue(BGAttributes.FIRE_RESISTANCE.get())));
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event) {
		LivingEntity target = event.getEntity();
		if(target.getAttribute(BGAttributes.ATTACKER_BURN_TIME.get()) != null) {
			if(target.getAttributeValue(BGAttributes.ATTACKER_BURN_TIME.get()) > 0 && event.getSource().getDirectEntity() instanceof LivingEntity attacker && !attacker.fireImmune()) {
				attacker.setSecondsOnFire((int) target.getAttributeValue(BGAttributes.ATTACKER_BURN_TIME.get()));
			}
		}

		if(event.getSource().getDirectEntity() instanceof LivingEntity attacker && attacker.getAttribute(BGAttributes.TARGET_BURN_TIME.get()) != null) {
			if(attacker.getAttributeValue(BGAttributes.TARGET_BURN_TIME.get()) > 0 && !target.fireImmune()) {
				int level = EnchantmentHelper.getFireAspect(attacker);
				int fireAspectTime = 0;
				if (level > 0) {
					fireAspectTime += (level * 4);
				}
				target.setSecondsOnFire((int) attacker.getAttributeValue(BGAttributes.TARGET_BURN_TIME.get()) + fireAspectTime);
			}
		}

		if(BGUtil.isEntityWearingAnyBrimsteel(event.getEntity())) {
			Level level = target.level();
			level.playSound((Player)null, target.getX(), target.getY(), target.getZ(), SoundEvents.BLAZE_HURT, target.getSoundSource(), 0.4F, (target.getRandom().nextFloat() - target.getRandom().nextFloat()) * 0.2F + 1.0F);
		}
	}

	@SubscribeEvent
	public static void onShieldBlock(ShieldBlockEvent event) {
		LivingEntity target = event.getEntity();
		if(target.getAttribute(BGAttributes.BLOCKED_ATTACKER_BURN_TIME.get()) != null) {
			if(target.getAttributeValue(BGAttributes.BLOCKED_ATTACKER_BURN_TIME.get()) > 0 && event.getDamageSource().getDirectEntity() instanceof LivingEntity attacker && !attacker.fireImmune()) {
				attacker.setSecondsOnFire((int) target.getAttributeValue(BGAttributes.BLOCKED_ATTACKER_BURN_TIME.get()));
			}
		}
	}

	@SubscribeEvent
	public static void getBreakSpeed(PlayerEvent.BreakSpeed event) {
		if(event.getEntity().getAttribute(BGAttributes.FLAMMABLE_BLOCK_BREAK_SPEED_BONUS.get()) != null) {
			double bonus = event.getEntity().getAttributeValue(BGAttributes.FLAMMABLE_BLOCK_BREAK_SPEED_BONUS.get());
			double test = event.getEntity().getAttributeValue(BGAttributes.FIRE_RESISTANCE.get());
			if(bonus > 0 && event.getState().ignitedByLava()) {
				event.setNewSpeed(event.getOriginalSpeed() + (float) bonus);
			}
		}
	}
}
