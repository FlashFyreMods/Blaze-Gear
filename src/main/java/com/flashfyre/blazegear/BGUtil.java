package com.flashfyre.blazegear;

import com.flashfyre.blazegear.registry.BGAttributes;
import com.flashfyre.blazegear.registry.BGItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Iterables;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import java.util.UUID;

public class BGUtil {

	public static final UUID TARGET_BURN_TIME_UUID = UUID.fromString("daf163b6-b5cb-4fe9-b21b-5616d6ed391b");
	public static final UUID FLAMMABLE_MINING_EFFICIENCY_UUID = UUID.fromString("eea9503d-56d0-4db1-a91e-d031eae514c6");
	public static final UUID SHIELD_BURN_TIME_UUID = UUID.fromString("d9abbf7b-020d-4adc-9336-7fb2f6fa4ff2");

	public static int getBlazeArmourCount(LivingEntity entity) {
		if(entity instanceof AbstractHorse horse) {
            Item item = horse.inventory.getItem(1).getItem();
			if (item == BGItems.BRIMSTEEL_HORSE_ARMOUR.get() || item == BGItems.NETHERSTEEL_HORSE_ARMOUR.get()) {
				return 4;
			}
		}
		int count = 0;
		Iterable<ItemStack> armourStacks = entity.getArmorSlots();
		if (Iterables.size(armourStacks) > 0) {
			for(ItemStack stack : armourStacks) {
				if(stack.getItem() instanceof ArmorItem armour && (armour.getMaterial() == BGItems.BRIMSTEEL_ARMOUR_MATERIAL || armour.getMaterial() == BGItems.NETHERSTEEL_ARMOUR_MATERIAL)) {
					count++;
				}
			}
		}
		return count;
	}

	public static ImmutableMultimap.Builder<Attribute, AttributeModifier> toolModifierBuilder(Tier tier) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
		float amount = tier == BGItems.BRIMSTEEL_TIER ? 4 : tier == BGItems.NETHERSTEEL_TIER ? 2 : 0;
		AttributeModifier targetBurnTimeMod = new AttributeModifier(BGUtil.TARGET_BURN_TIME_UUID, "Target burn time", amount, AttributeModifier.Operation.ADDITION);
		AttributeModifier flammableBlockEfficiencyMod = new AttributeModifier(BGUtil.FLAMMABLE_MINING_EFFICIENCY_UUID, "Flammable mining efficiency", 5F, AttributeModifier.Operation.ADDITION);
		builder.put(BGAttributes.TARGET_BURN_TIME.get(), targetBurnTimeMod);
		builder.put(BGAttributes.FLAMMABLE_BLOCK_BREAK_SPEED_BONUS.get(), flammableBlockEfficiencyMod);
		return builder;
	}
}
