package com.flashfyre.blazegear.registry;

import com.flashfyre.blazegear.BlazeGear;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BGAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, BlazeGear.MOD_ID);

    public static final RegistryObject<Attribute> FIRE_RESISTANCE = ATTRIBUTES.register("generic.fire_resistance", () ->
            new RangedAttribute("attribute.blazegear.name.generic.fire_resistance", 0.0D, 0.0D, 1.0D));

    public static final RegistryObject<Attribute> ATTACKER_BURN_TIME = ATTRIBUTES.register("generic.attacker_burn_time", () ->
            new RangedAttribute("attribute.blazegear.name.generic.attacker_burn_time", 0.0D, 0.0D, 1024.0D));

    public static final RegistryObject<Attribute> BLOCKED_ATTACKER_BURN_TIME = ATTRIBUTES.register("generic.blocked_attacker_burn_time", () ->
            new RangedAttribute("attribute.blazegear.name.generic.blocked_attacker_burn_time", 0.0D, 0.0D, 1024.0D));

    public static final RegistryObject<Attribute> TARGET_BURN_TIME = ATTRIBUTES.register("generic.target_burn_time", () ->
            new RangedAttribute("attribute.blazegear.name.generic.target_burn_time", 0.0D, 0.0D, 1024.0D));

    public static final RegistryObject<Attribute> FLAMMABLE_BLOCK_BREAK_SPEED_BONUS = ATTRIBUTES.register("player.flammable_block_break_speed_bonus", () ->
            new RangedAttribute("attribute.blazegear.name.player.flammable_block_break_speed_bonus", 0.0D, 0.0D, 1024.0D).setSyncable(true));
}
