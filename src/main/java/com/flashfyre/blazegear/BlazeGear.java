package com.flashfyre.blazegear;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flashfyre.blazegear.client.BrimsteelParticlePacket;
import com.flashfyre.blazegear.registry.BGBlocks;
import com.flashfyre.blazegear.registry.BGItems;
import com.flashfyre.blazegear.registry.BGLootModifiers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid=BlazeGear.MOD_ID)
@Mod(BlazeGear.MOD_ID)
public class BlazeGear
{
	public static final String MOD_ID = "blazegear";
	public static BlazeGear instance;
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String SIMPLE_CHANNEL_PROTOCOL_VERSION = "1";
	public static final SimpleChannel SIMPLE_CHANNEL = NetworkRegistry.newSimpleChannel(
		    new ResourceLocation("blazegear", "main"),
		    () -> SIMPLE_CHANNEL_PROTOCOL_VERSION,
		    SIMPLE_CHANNEL_PROTOCOL_VERSION::equals,
		    SIMPLE_CHANNEL_PROTOCOL_VERSION::equals
		);
    		
    public BlazeGear() {
        instance = this;
        
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        MinecraftForge.EVENT_BUS.register(this);
        
        BGItems.ITEMS.register(modBus);
        BGBlocks.BLOCKS.register(modBus);
        BGLootModifiers.LOOT_MODIFIERS.register(modBus);
    }
    
    @SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		SIMPLE_CHANNEL.registerMessage(1, BrimsteelParticlePacket.class, BrimsteelParticlePacket::encode, BrimsteelParticlePacket::decode, BrimsteelParticlePacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}
    
    @SubscribeEvent
	public static void buildContents(BuildCreativeModeTabContentsEvent event) {
    	if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
    		event.accept(BGItems.BRIMSTEEL_SHOVEL);
			event.accept(BGItems.BRIMSTEEL_PICKAXE);
			event.accept(BGItems.BRIMSTEEL_AXE);
			event.accept(BGItems.BRIMSTEEL_HOE);
			event.accept(BGItems.FLINT_AND_BRIMSTEEL);
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
        	event.accept(BGItems.BRIMSTEEL_SWORD);
			event.accept(BGItems.BRIMSTEEL_HELMET);
			event.accept(BGItems.BRIMSTEEL_CHESTPLATE);
			event.accept(BGItems.BRIMSTEEL_LEGGINGS);
			event.accept(BGItems.BRIMSTEEL_BOOTS);
			event.accept(BGItems.BRIMSTEEL_HORSE_ARMOUR);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(BGItems.BRIMSTEEL_INGOT);
			
		} else if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(BGBlocks.BRIMSTEEL_BLOCK);
		}
    }
}

