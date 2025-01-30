package com.flashfyre.blazegear.client;

import com.flashfyre.blazegear.client.model.BaseBlazeArmsModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Predicate;

public class BlazeArmsLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T,M> {
    public static final ResourceLocation TEXTURE_BLAZE_ARMS = new ResourceLocation("textures/entity/blaze.png");
    private final BaseBlazeArmsModel<LivingEntity> model;
    private final Predicate<T> renderWhen;

    public BlazeArmsLayer(LivingEntityRenderer<T, M> owner, BaseBlazeArmsModel<LivingEntity> model, Predicate<T> renderWhen) {
        super(owner);
        this.model = model;
        this.renderWhen = renderWhen;
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int lightness,
                       T entity, float limbSwing, float limbSwingAmount, float partialTicks,
                       float ageInTicks, float netHeadYaw, float headPitch) {
        if(this.renderWhen.test(entity)) {
            stack.pushPose();
            renderColoredCutoutModel(this.model, TEXTURE_BLAZE_ARMS, stack, buffer, 15728880, entity, 1.0F, 1.0F, 1.0F);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            stack.popPose();
        }
    }
}
