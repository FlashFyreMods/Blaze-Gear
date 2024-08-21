package com.flashfyre.blazegear.client;

import java.util.Arrays;

import com.flashfyre.blazegear.BGUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlazeArmsLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T,M>
{
	public static final ResourceLocation TEXTURE_BLAZE_ARMS = new ResourceLocation("textures/entity/blaze.png");
    private final BlazeArmsModel<LivingEntity> model;
    
    public BlazeArmsLayer(LivingEntityRenderer<T, M> owner) {
        super(owner);
        this.model = new BlazeArmsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.BLAZE_ARMS_LAYER));
    }

	@Override
	public void render(PoseStack matrixStack, MultiBufferSource buffer, int lightness,
			T entity, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {
		if(BGUtil.isEntityWearingFullBrimsteel(entity)) {
			matrixStack.pushPose();
			renderColoredCutoutModel(this.model, TEXTURE_BLAZE_ARMS, matrixStack, buffer, 15728880, entity, 1.0F, 1.0F, 1.0F);
			this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			matrixStack.popPose();
		}		
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class BlazeArmsModel<T extends LivingEntity>  extends ListModel<T> {
		
		private final ModelPart[] blazeSticks;
		private final ImmutableList<ModelPart> parts;
		
		private float r3 = 7.0F;	
		
		public BlazeArmsModel(ModelPart modelPart) {
			this.blazeSticks = new ModelPart[12];
			Arrays.setAll(this.blazeSticks, (num) -> {
		         return modelPart.getChild(getPartName(num));
			});
			
			Builder<ModelPart> builder = ImmutableList.builder();
			builder.addAll(Arrays.asList(this.blazeSticks));
		    this.parts = builder.build();
		}
		
		private static String getPartName(int num) {
		      return "part" + num;
		   }
		
		public static LayerDefinition createBodyLayer() {
			MeshDefinition meshdefinition = new MeshDefinition();
	        PartDefinition partdefinition = meshdefinition.getRoot();
	        float f = 0.0F;
	        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
	        for(int i = 0; i < 4; ++i) {
	            float f1 = Mth.cos(f) * 9.0F;
	            float f2 = -2.0F + Mth.cos((float)(i * 2) * 0.25F);
	            float f3 = Mth.sin(f) * 9.0F;
	            partdefinition.addOrReplaceChild(getPartName(i), cubelistbuilder, PartPose.offset(f1, f2, f3));
	            ++f;
	         }

	         f = ((float)Math.PI / 4F);

	         for(int j = 4; j < 8; ++j) {
	            float f4 = Mth.cos(f) * 7.0F;
	            float f6 = 2.0F + Mth.cos((float)(j * 2) * 0.25F);
	            float f8 = Mth.sin(f) * 7.0F;
	            partdefinition.addOrReplaceChild(getPartName(j), cubelistbuilder, PartPose.offset(f4, f6, f8));
	            ++f;
	         }

	         f = 0.47123894F;

	         for(int k = 8; k < 12; ++k) {
	            float f5 = Mth.cos(f) * 5.0F;
	            float f7 = 11.0F + Mth.cos((float)k * 1.5F * 0.5F);
	            float f9 = Mth.sin(f) * 5.0F;
	            partdefinition.addOrReplaceChild(getPartName(k), cubelistbuilder, PartPose.offset(f5, f7, f9));
	            ++f;
	         }
	        return LayerDefinition.create(meshdefinition, 64, 32);
		}

		@Override
		public Iterable<ModelPart> parts() {
			return this.parts;
		}

		@Override
		public void setupAnim(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) 
		{	
			
			
			float ySpeed = 1.0F;
			if(livingEntity.level().isRainingAt(livingEntity.blockPosition())) {
				float rainStrength = livingEntity.level().getRainLevel(1.0F);
				ySpeed = ySpeed + (1.5F * rainStrength);
			}
			
			float f = ageInTicks * (float)Math.PI * -0.1F;
			float y1 = -5.0F; // Note that negative values are higher up and vice versa
			float y2 = 0.0F;
			float y3 = 13.1F;
			float r1 = 14.0F;
			float r2 = 12.0F;
			
			if(livingEntity.isCrouching()) 
			{
				y1 += 3.0F;
				y2 += 4.0F;
				y3 += 0.1F;
				if(r3 <= 9.0F) {
					this.r3 += 0.1F;
				}				
			}
			else {
				if(r3 >= 7.0F) {
					this.r3 -= 0.1F;
				}				
			}
			
			
			
	        for (int i = 0; i < 4; ++i)
	        {
	            this.blazeSticks[i].y = y1 + (Mth.cos(((float)(i * 2) + ageInTicks) * 0.25F) * (ySpeed));
	            this.blazeSticks[i].x = (Mth.cos(f) * r1) - 1;
	            this.blazeSticks[i].z = Mth.sin(f) * r1;
	            ++f;
	        }

	        f = ((float)Math.PI / 4F) + ageInTicks * (float)Math.PI * 0.03F;

	        for (int j = 4; j < 8; ++j)
	        {
	            this.blazeSticks[j].y = y2 + (Mth.cos(((float)(j * 2) + ageInTicks) * 0.25F) * ySpeed);
	            this.blazeSticks[j].x = (Mth.cos(f) * r2) - 1;
	            this.blazeSticks[j].z = Mth.sin(f) * r2;
	            ++f;
	        }

	        f = 0.47123894F + ageInTicks * (float)Math.PI * -0.05F;

	        for (int k = 8; k < 12; ++k)
	        {
	            this.blazeSticks[k].y = y3 + (Mth.cos(((float)k * 1.5F + ageInTicks) * 0.5F) * ySpeed);
	            this.blazeSticks[k].x = (Mth.cos(f) * r3) - 1;
	            this.blazeSticks[k].z = Mth.sin(f) * r3;
	            ++f;
	        }
		}		
	}
}
