package com.flashfyre.blazegear.registry;

import com.flashfyre.blazegear.BlazeGear;
import com.flashfyre.blazegear.loot.BrimsteelSmeltingLootModifier;
import com.flashfyre.blazegear.loot.TableInjectLootModifier;
import com.mojang.serialization.Codec;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BGLootModifiers {
	
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BlazeGear.MOD_ID);
	
	public static final RegistryObject<Codec<BrimsteelSmeltingLootModifier>> BRIMSTEEL_SMELTING = LOOT_MODIFIERS.register("brimsteel_smelting", () -> BrimsteelSmeltingLootModifier.CODEC);
	public static final RegistryObject<Codec<TableInjectLootModifier>> TABLE_INJECT = LOOT_MODIFIERS.register("inject_from_loot_table", () -> TableInjectLootModifier.CODEC);
}
