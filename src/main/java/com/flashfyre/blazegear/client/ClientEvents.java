package com.flashfyre.blazegear.client;

import com.flashfyre.blazegear.BlazeGear;

import com.flashfyre.blazegear.client.model.LowerBlazeArmsModel;
import com.flashfyre.blazegear.client.model.MiddleBlazeArmsModel;
import com.flashfyre.blazegear.client.model.UpperBlazeArmsModel;
import com.flashfyre.blazegear.registry.BGItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BlazeGear.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    public static ModelLayerLocation UPPER_BLAZE_ARMS_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "blazegear_upper_blaze_arms");
    public static ModelLayerLocation MIDDLE_BLAZE_ARMS_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "blazegear_middle_blaze_arms");
    public static ModelLayerLocation LOWER_BLAZE_ARMS_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "blazegear_lower_blaze_arms");
	
	@SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UPPER_BLAZE_ARMS_LAYER, UpperBlazeArmsModel::createLayer);
        event.registerLayerDefinition(MIDDLE_BLAZE_ARMS_LAYER, MiddleBlazeArmsModel::createLayer);
        event.registerLayerDefinition(LOWER_BLAZE_ARMS_LAYER, LowerBlazeArmsModel::createLayer);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(BGItems.BRIMSTEEL_SHIELD.get(), new ResourceLocation("blocking"), (stack, level, livingEntity, seed) -> {
                return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
            });
            ItemProperties.register(BGItems.NETHERSTEEL_SHIELD.get(), new ResourceLocation("blocking"), (stack, level, livingEntity, seed) -> {
                return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
            });
        });
    }
}
