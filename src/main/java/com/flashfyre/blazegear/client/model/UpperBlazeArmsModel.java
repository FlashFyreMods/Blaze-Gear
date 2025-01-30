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

public class UpperBlazeArmsModel extends BaseBlazeArmsModel<LivingEntity> {
    public UpperBlazeArmsModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(@NotNull LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        float ySpeed = 1.0F;
        float y1 = -5.0F; // Note that negative values are higher up and vice versa
        float f = ageInTicks * (float)Math.PI * -0.1F;
        float r1 = 14.0F;

        for (int i = 0; i < 4; ++i) {
            this.blazeSticks[i].y = y1 + (Mth.cos(((float)(i * 2) + ageInTicks) * 0.25F) * (ySpeed));
            this.blazeSticks[i].x = (Mth.cos(f) * r1) - 1;
            this.blazeSticks[i].z = Mth.sin(f) * r1;
            ++f;
        }
    }

    public static LayerDefinition createLayer() {
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
        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}
