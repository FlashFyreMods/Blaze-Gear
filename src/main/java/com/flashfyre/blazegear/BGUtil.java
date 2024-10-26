package com.flashfyre.blazegear;

import com.flashfyre.blazegear.registry.BGAttributes;
import com.flashfyre.blazegear.registry.BGItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Iterables;

import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class BGUtil {
	
	/**
	 * Sends particle  packet to every player in serverLevel except for the specified player.
	 * 
	 * @param <T>
	 * @param serverLevel
	 * @param exception
	 * @param particle
	 * @param x
	 * @param y
	 * @param z
	 * @param count
	 * @param xDist
	 * @param yDist
	 * @param zDist
	 * @param maxSpeed
	 */
	public static <T extends ParticleOptions> void sendParticles(ServerLevel serverLevel, ServerPlayer exception, T particle, double x, double y, double z, int count, double xDist, double yDist, double zDist, double maxSpeed) {
		for(int i = 0; i < serverLevel.players().size(); ++i) {
			ServerPlayer serverPlayer = serverLevel.players().get(i);
			if(serverPlayer == null || serverPlayer != exception) {
				serverLevel.sendParticles(serverPlayer, particle, false, x, y, z, count, xDist, yDist, zDist, maxSpeed);
			}	         
		}
	}

	public static boolean isHorseWithBrimsteelArmourEquipped(LivingEntity entity) {
		if(entity instanceof AbstractHorse horse) {
			if(horse.inventory.getItem(1).getItem() == BGItems.BRIMSTEEL_HORSE_ARMOUR.get()) {
				return true;
			}
		}
		return false;
	}
		
	public static boolean isEntityWearingFullBrimsteel(LivingEntity entity) {
		if(entity instanceof AbstractHorse) {
			AbstractHorse horse = (AbstractHorse) entity;
			SimpleContainer inventory = horse.inventory;
			if(inventory.getItem(1).getItem() == BGItems.BRIMSTEEL_HORSE_ARMOUR.get()) {
				return true;
			}
			return false;
		}
		else {
			Iterable<ItemStack> iterable = entity.getArmorSlots();
	    	if (iterable == null || Iterables.size(iterable) == 0) {
	    		return false;
	        } 
	    	else {
	    		for(ItemStack stack : iterable) {
	    			if(stack.getItem() instanceof ArmorItem) {
	    				ArmorItem item = (ArmorItem) stack.getItem();
	    				if(item.getMaterial() == BGItems.ARMOUR_BRIMSTEEL) { // If any of the armour isn't brimsteel return false
	    					continue;
	    				}
	    			}
	    			return false;
	    		}
	    		return true; // If it makes it to here then all armour is brimsteel
	    	}
		}    	
    }

	public static boolean isEntityWearingAnyBrimsteel(LivingEntity entity) {
		if(entity instanceof AbstractHorse) {
			AbstractHorse horse = (AbstractHorse) entity;
			SimpleContainer inventory = horse.inventory;
			if(inventory.getItem(1).getItem() == BGItems.BRIMSTEEL_HORSE_ARMOUR.get()) {
				return true;
			}
		}
		else {
			Iterable<ItemStack> armourStacks = entity.getArmorSlots();
			if (armourStacks != null && Iterables.size(armourStacks) > 0) {
				for(ItemStack stack : armourStacks) {
					if(stack.getItem() instanceof ArmorItem item && item.getMaterial() == BGItems.ARMOUR_BRIMSTEEL) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static ImmutableMultimap.Builder<Attribute, AttributeModifier> toolModifierBuilder(EquipmentSlot slot, ItemStack stack) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<Attribute, AttributeModifier>();
		AttributeModifier targetBurnTimeMod = new AttributeModifier(BGItems.SLOT_MODIFIER_UUIDS[slot.getIndex()], "Target burn time", 4F, AttributeModifier.Operation.ADDITION);
		AttributeModifier flammableBlockEfficiencyMod = new AttributeModifier(BGItems.SLOT_MODIFIER_UUIDS[slot.getIndex()], "Flammable mining efficiency", 5F, AttributeModifier.Operation.ADDITION);
		builder.put(BGAttributes.TARGET_BURN_TIME.get(), targetBurnTimeMod);
		builder.put(BGAttributes.FLAMMABLE_BLOCK_BREAK_SPEED_BONUS.get(), flammableBlockEfficiencyMod);
		return builder;
	}
}
