package com.flashfyre.blazegear.client;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.network.NetworkEvent;

public class ClientPacketHandler {
	
	/**
	 * Spawns a smoke particle on the client if the camera is not first person or the entity in the packet is different to the client.
	 * 
	 * @param packet
	 * @param ctx
	 */
	public static void handleBrimsteelParticlePacket(BrimsteelParticlePacket packet, Supplier<NetworkEvent.Context> ctx) {
		Minecraft instance = Minecraft.getInstance();
		ClientLevel level = instance.level;
		if(instance.player != null) {
			if(!instance.options.getCameraType().isFirstPerson() || packet.entityId != instance.player.getId()) {
				for(int n = 0; n < 2; ++n) {
					level.addParticle(ParticleTypes.LARGE_SMOKE, false, packet.x, packet.y, packet.z, 0, 0, 0);
		        }			
			}	
		}			
	}
}
