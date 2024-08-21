package com.flashfyre.blazegear;

import com.flashfyre.blazegear.client.BrimsteelParticlePacket;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
				// We send a particle to clients tracking the chunk telling them to spawn particles at this entity
				BlazeGear.SIMPLE_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> entity.getCommandSenderWorld().getChunkAt(entity.blockPosition())), new BrimsteelParticlePacket(entity.getId(), entity.getRandomX(xzSpread), entity.getRandomY(), entity.getRandomZ(xzSpread)));		
			}			
		}		
	}
	
	@SubscribeEvent
	public static void preventHotFloorAndOnFireDamage(LivingAttackEvent event) {		
		if(BGUtil.isEntityWearingFullBrimsteel(event.getEntity())) {
			Level level = event.getEntity().level();
			if(event.getSource() == level.damageSources().hotFloor() || event.getSource() == level.damageSources().onFire()) {
				event.setCanceled(true);
			}		
		}
	}
	
	@SubscribeEvent
	public static void halfInFireDamage(LivingHurtEvent event) {
		if(BGUtil.isEntityWearingFullBrimsteel(event.getEntity())) {
			if(event.getSource() == event.getEntity().level().damageSources().inFire()) {
				event.setAmount(event.getAmount()/2);
			}		
		}
	}
}
