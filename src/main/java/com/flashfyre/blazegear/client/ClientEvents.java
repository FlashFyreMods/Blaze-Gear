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
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BlazeGear.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    public static ModelLayerLocation UPPER_BLAZE_ARMS_LAYER = new ModelLayerLocation(new ResourceLocation("mineraft:player"), "blazegear_upper_blaze_arms");
    public static ModelLayerLocation MIDDLE_BLAZE_ARMS_LAYER = new ModelLayerLocation(new ResourceLocation("mineraft:player"), "blazegear_middle_blaze_arms");
    public static ModelLayerLocation LOWER_BLAZE_ARMS_LAYER = new ModelLayerLocation(new ResourceLocation("mineraft:player"), "blazegear_lower_blaze_arms");
	
	@SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UPPER_BLAZE_ARMS_LAYER, UpperBlazeArmsModel::createLayer);
        event.registerLayerDefinition(MIDDLE_BLAZE_ARMS_LAYER, MiddleBlazeArmsModel::createLayer);
        event.registerLayerDefinition(LOWER_BLAZE_ARMS_LAYER, LowerBlazeArmsModel::createLayer);
    }
	
	@SubscribeEvent
    public static void construct(EntityRenderersEvent.AddLayers event) {
        addLayerToPlayerSkin(event, "default");
        addLayerToPlayerSkin(event, "slim");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName) {
        EntityRenderer<? extends Player> render = event.getSkin(skinName);
        if (render instanceof LivingEntityRenderer livingRenderer) {
            livingRenderer.addLayer(new BlazeArmsLayer(livingRenderer,
                    new UpperBlazeArmsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.UPPER_BLAZE_ARMS_LAYER)),
                    (Object e) -> ((LivingEntity) e).getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem helmet && helmet.getMaterial() == BGItems.ARMOUR_BRIMSTEEL));
            livingRenderer.addLayer(new BlazeArmsLayer(livingRenderer,
                    new MiddleBlazeArmsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.MIDDLE_BLAZE_ARMS_LAYER)),
                    (Object e) -> ((LivingEntity) e).getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem chestplate && chestplate.getMaterial() == BGItems.ARMOUR_BRIMSTEEL));
            livingRenderer.addLayer(new BlazeArmsLayer(livingRenderer,
                    new LowerBlazeArmsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.LOWER_BLAZE_ARMS_LAYER)),
                    (Object e) ->
                            ((LivingEntity) e).getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem legs && legs.getMaterial() == BGItems.ARMOUR_BRIMSTEEL ||
                            ((LivingEntity) e).getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem boots && boots.getMaterial() == BGItems.ARMOUR_BRIMSTEEL));
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(BGItems.BRIMSTEEL_SHIELD.get(), new ResourceLocation("blocking"), (stack, level, livingEntity, seed) -> {
                return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
            });
        });
    }
}
