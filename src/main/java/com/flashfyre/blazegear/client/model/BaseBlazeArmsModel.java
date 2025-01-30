package com.flashfyre.blazegear.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class BaseBlazeArmsModel<T extends LivingEntity>  extends ListModel<T> {

    protected final ModelPart[] blazeSticks;
    private final ImmutableList<ModelPart> parts;

    public BaseBlazeArmsModel(ModelPart modelPart) {
        this.blazeSticks = new ModelPart[4];
        Arrays.setAll(this.blazeSticks, (num) -> {
            return modelPart.getChild(getPartName(num));
        });

        ImmutableList.Builder<ModelPart> builder = ImmutableList.builder();
        builder.addAll(Arrays.asList(this.blazeSticks));
        this.parts = builder.build();
    }

    protected static String getPartName(int num) {
        return "part" + num;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        float f = 0.0F;
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
        for (int i = 0; i < 4; ++i) {
            float f1 = Mth.cos(f) * 9.0F;
            float f2 = -2.0F + Mth.cos((float) (i * 2) * 0.25F);
            float f3 = Mth.sin(f) * 9.0F;
            partdefinition.addOrReplaceChild(getPartName(i), cubelistbuilder, PartPose.offset(f1, f2, f3));
            ++f;
        }
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public @NotNull Iterable<ModelPart> parts() {
        return this.parts;
    }
}