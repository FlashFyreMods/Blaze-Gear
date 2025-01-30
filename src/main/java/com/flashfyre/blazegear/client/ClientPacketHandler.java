package com.flashfyre.blazegear.client;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.network.NetworkEvent;

public class ClientPacketHandler {
	
	/**
	 * Spawns a smoke particle on the client at an entity wearing brimsteel.
	 * If the client is the same as the entity wearing the brimsteel, this only happens if the camera is not first person.
	 * 
	 * @param packet
	 * @param ctx
	 */
	public static void handleBrimsteelParticlePacket(BrimsteelParticlePacket packet, Supplier<NetworkEvent.Context> ctx) {
		Minecraft instance = Minecraft.getInstance();
		ClientLevel level = instance.level;
		if(instance.player != null) {
			if(packet.entityId != instance.player.getId() || !instance.options.getCameraType().isFirstPerson()) {
				for(int n = 0; n < packet.count; ++n) {
					level.addParticle(ParticleTypes.LARGE_SMOKE, false, packet.x, packet.y, packet.z, 0, 0, 0);
		        }			
			}	
		}			
	}
}
