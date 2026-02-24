package com.flashfyre.blazegear.mixin;

import com.flashfyre.blazegear.client.BlazeArmsLayer;
import com.flashfyre.blazegear.client.ClientEvents;
import com.flashfyre.blazegear.client.model.BaseBlazeArmsModel;
import com.flashfyre.blazegear.client.model.LowerBlazeArmsModel;
import com.flashfyre.blazegear.client.model.MiddleBlazeArmsModel;
import com.flashfyre.blazegear.client.model.UpperBlazeArmsModel;
import com.flashfyre.blazegear.registry.BGItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    @Unique
    private final BaseBlazeArmsModel<LivingEntity> blazeGear$upperModel = new UpperBlazeArmsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.UPPER_BLAZE_ARMS_LAYER));
    @Unique
    private final BaseBlazeArmsModel<LivingEntity> blazeGear$middleModel = new MiddleBlazeArmsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.MIDDLE_BLAZE_ARMS_LAYER));
    @Unique
    private final BaseBlazeArmsModel<LivingEntity> blazeGear$lowerModel = new LowerBlazeArmsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.LOWER_BLAZE_ARMS_LAYER));

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Inject(at = @At("HEAD"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", cancellable = true)
    public void render(PoseStack stack, MultiBufferSource buffer, int lightness, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callback) {
        stack.pushPose();
        if(blazeGear$isBrimsteelMaterial(entity.getItemBySlot(EquipmentSlot.HEAD).getItem())) {
            renderColoredCutoutModel(this.blazeGear$upperModel, BlazeArmsLayer.TEXTURE_BLAZE_ARMS, stack, buffer, 15728880, entity, 1.0F, 1.0F, 1.0F);
            this.blazeGear$upperModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
        if(blazeGear$isBrimsteelMaterial(entity.getItemBySlot(EquipmentSlot.CHEST).getItem())) {
            renderColoredCutoutModel(this.blazeGear$middleModel, BlazeArmsLayer.TEXTURE_BLAZE_ARMS, stack, buffer, 15728880, entity, 1.0F, 1.0F, 1.0F);
            this.blazeGear$middleModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
        if(blazeGear$isBrimsteelMaterial(entity.getItemBySlot(EquipmentSlot.LEGS).getItem()) || blazeGear$isBrimsteelMaterial(entity.getItemBySlot(EquipmentSlot.FEET).getItem())) {
            renderColoredCutoutModel(this.blazeGear$lowerModel, BlazeArmsLayer.TEXTURE_BLAZE_ARMS, stack, buffer, 15728880, entity, 1.0F, 1.0F, 1.0F);
            this.blazeGear$lowerModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
        stack.popPose();
    }

    @Unique
    private static boolean blazeGear$isBrimsteelMaterial(Item item) {
        if(item instanceof ArmorItem armour) {
            return armour.getMaterial() == BGItems.BRIMSTEEL_ARMOUR_MATERIAL || armour.getMaterial() == BGItems.NETHERSTEEL_ARMOUR_MATERIAL;
        }
        return false;
    }
}
