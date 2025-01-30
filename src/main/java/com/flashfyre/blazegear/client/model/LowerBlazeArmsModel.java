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

public class LowerBlazeArmsModel extends BaseBlazeArmsModel<LivingEntity> {
    public LowerBlazeArmsModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(@NotNull LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        float ySpeed = 1.0F;
        float y3 = 13.1F;
        float f = ageInTicks * (float)Math.PI * -0.1F;
        float r3 = 7.0F;

        f = 0.47123894F + ageInTicks * (float)Math.PI * -0.05F;

        for (int k = 0; k < 4; ++k)
        {
            this.blazeSticks[k].y = y3 + (Mth.cos(((float)k * 1.5F + ageInTicks) * 0.5F) * ySpeed);
            this.blazeSticks[k].x = (Mth.cos(f) * r3) - 1;
            this.blazeSticks[k].z = Mth.sin(f) * r3;
            ++f;
        }
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
        float f = 0.47123894F;

        for(int k = 0; k < 4; ++k) {
            float f5 = Mth.cos(f) * 5.0F;
            float f7 = 11.0F + Mth.cos((float)k * 1.5F * 0.5F);
            float f9 = Mth.sin(f) * 5.0F;
            partdefinition.addOrReplaceChild(getPartName(k), cubelistbuilder, PartPose.offset(f5, f7, f9));
            ++f;
        }
        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}
