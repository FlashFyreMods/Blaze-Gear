package com.flashfyre.blazegear.registry;

import java.util.List;
import java.util.UUID;

import com.flashfyre.blazegear.BlazeGear;
import com.flashfyre.blazegear.item.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BGItems {
	
	private static final TagKey<Block> BRIMSTEEL_TIER_TAG = BlockTags.create(new ResourceLocation("blazegear:needs_brimsteel_tool"));
	public static final Tier BRIMSTEEL_TOOL_TIER = TierSortingRegistry.registerTier(
			new ForgeTier(3, 1750, 7.5F, 3.0F, 16, BRIMSTEEL_TIER_TAG, () -> Ingredient.of(BGItems.BRIMSTEEL_INGOT.get())), 
			new ResourceLocation("blazegear:brimsteel"), 
			List.of(Tiers.DIAMOND), 
			List.of(Tiers.NETHERITE));
    
    private final static int[] BRIMSTEEL_ARMOUR_VALUES = new int[]{3, 6, 8, 3};

	public static final UUID[] SLOT_MODIFIER_UUIDS = {
			UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
			UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
			UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
			UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
	};
    
	public static final ArmorMaterial ARMOUR_BRIMSTEEL = new ArmorMaterial()
    {    	
    	@Override
		public String getName() {
			return BlazeGear.MOD_ID+":brimsteel";
		}

		@Override
		public int getDurabilityForType(ArmorItem.Type type) {
			return switch(type) {
			case HELMET -> 11 * 35;
			case CHESTPLATE -> 16 * 35;
			case LEGGINGS -> 15 * 35;
			case BOOTS -> 13 * 35;
			default -> 0;
			};
		}

		@Override
		public int getDefenseForType(Type slotIn) {
			return BRIMSTEEL_ARMOUR_VALUES[slotIn.getSlot().getIndex()];
		}

		@Override
		public int getEnchantmentValue() {
			return 16;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_NETHERITE;
		}
		
		@Override
		public float getToughness() {
			return 1.5F;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(BGItems.BRIMSTEEL_INGOT.get());
		}

		@Override
		public float getKnockbackResistance() {
			return 0.05F;
		}
    };
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BlazeGear.MOD_ID);
	
	public static final RegistryObject<Item> BRIMSTEEL_INGOT = ITEMS.register("brimsteel_ingot", () -> new FuelItem(6000, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_SHIELD = ITEMS.register("brimsteel_shield", () -> new BrimsteelShieldItem(new Item.Properties().durability(840).fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_SWORD = ITEMS.register("brimsteel_sword", () -> new BrimsteelSwordItem(BRIMSTEEL_TOOL_TIER, 3, -2.4F, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_AXE = ITEMS.register("brimsteel_axe", () -> new BrimsteelAxeItem(BRIMSTEEL_TOOL_TIER, 5.0F, -3.0F, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_PICKAXE = ITEMS.register("brimsteel_pickaxe", () -> new BrimsteelPickaxeItem(BRIMSTEEL_TOOL_TIER, 1, -2.4F, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_SHOVEL = ITEMS.register("brimsteel_shovel", () -> new BrimsteelShovelItem(BRIMSTEEL_TOOL_TIER, 1.5F, -2.4F, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_HOE = ITEMS.register("brimsteel_hoe", () -> new BrimsteelHoeItem(BRIMSTEEL_TOOL_TIER, -3, 0.0F, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_HELMET = ITEMS.register("brimsteel_helmet", () -> new BrimsteelArmourItem(ARMOUR_BRIMSTEEL, Type.HELMET, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_CHESTPLATE = ITEMS.register("brimsteel_chestplate", () -> new BrimsteelArmourItem(ARMOUR_BRIMSTEEL, Type.CHESTPLATE, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_LEGGINGS = ITEMS.register("brimsteel_leggings", () -> new BrimsteelArmourItem(ARMOUR_BRIMSTEEL, Type.LEGGINGS, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_BOOTS = ITEMS.register("brimsteel_boots", () -> new BrimsteelArmourItem(ARMOUR_BRIMSTEEL, Type.BOOTS, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> FLINT_AND_BRIMSTEEL = ITEMS.register("flint_and_brimsteel", () -> new FlintAndSteelItem(new Item.Properties().durability(448).fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_HORSE_ARMOUR = ITEMS.register("brimsteel_horse_armour", () -> new BrimsteelHorseArmourItem(10, new Item.Properties().stacksTo(1).fireResistant()));
	public static final RegistryObject<Item> BRIMSTEEL_NUGGET = ITEMS.register("brimsteel_nugget", () -> new FuelItem(666, new Item.Properties().fireResistant()));
}