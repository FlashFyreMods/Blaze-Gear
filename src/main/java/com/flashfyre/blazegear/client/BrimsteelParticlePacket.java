package com.flashfyre.blazegear.client;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;

public class BrimsteelParticlePacket {
	protected final double xzSpread;
	protected final int entityId;
	protected final int count;
	
	public BrimsteelParticlePacket(int entityId, double xzSpread, int count) {
		this.entityId = entityId;
		this.xzSpread = xzSpread;
		this.count = count;
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(entityId);
		buffer.writeDouble(xzSpread);
		buffer.writeInt(count);
	}
	
	public static BrimsteelParticlePacket decode(FriendlyByteBuf buffer) {
	    return new BrimsteelParticlePacket(buffer.readInt(), buffer.readDouble(), buffer.readInt());
	}

	public static void handle(BrimsteelParticlePacket packet, Supplier<NetworkEvent.Context> ctx) {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ctx.get().enqueueWork(() ->
			ClientPacketHandler.handleBrimsteelParticlePacket(packet, ctx));
		}
	    ctx.get().setPacketHandled(true);
	}
}
