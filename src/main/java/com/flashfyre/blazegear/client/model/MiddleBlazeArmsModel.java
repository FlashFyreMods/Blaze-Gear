package com.flashfyre.blazegear.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class MiddleBlazeArmsModel extends BaseBlazeArmsModel<LivingEntity> {
    public MiddleBlazeArmsModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(@NotNull LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        float ySpeed = 1.0F;
        float y = 0.0F;
        float r = 12.0F;

        float f = ((float)Math.PI / 4F) + ageInTicks * (float)Math.PI * 0.03F;

        for (int j = 0; j < 4; ++j)
        {
            this.blazeSticks[j].y = y + (Mth.cos(((float)(j * 2) + ageInTicks) * 0.25F) * ySpeed);
            this.blazeSticks[j].x = (Mth.cos(f) * r) - 1;
            this.blazeSticks[j].z = Mth.sin(f) * r;
            ++f;
        }
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        CubeListBuilder builder = CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
        float f = ((float)Math.PI / 4F);

        for(int j = 0; j < 4; ++j) {
            float f4 = Mth.cos(f) * 7.0F;
            float f6 = 2.0F + Mth.cos((float)(j * 2) * 0.25F);
            float f8 = Mth.sin(f) * 7.0F;
            part.addOrReplaceChild(getPartName(j), builder, PartPose.offset(f4, f6, f8));
            ++f;
        }
        return LayerDefinition.create(mesh, 64, 32);
    }
}
