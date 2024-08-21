package com.flashfyre.blazegear.registry;

import java.util.function.Supplier;

import com.flashfyre.blazegear.BlazeGear;
import com.flashfyre.blazegear.blocks.BrimsteelBlock;
import com.flashfyre.blazegear.items.BrimsteelBlockItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BGBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BlazeGear.MOD_ID);
	
	public static final RegistryObject<Block> BRIMSTEEL_BLOCK = registerBrimsteelBlockAndItem("brimsteel_block", () -> new BrimsteelBlock(Block.Properties.of().mapColor(MapColor.GOLD).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.NETHERITE_BLOCK).lightLevel((state) -> 7).emissiveRendering((state, world, pos) -> true))); 
	
	private static RegistryObject<Block> registerBrimsteelBlockAndItem(final String name, final Supplier<Block> block) {
		final RegistryObject<Block> registryObject = BLOCKS.register(name, block);
		BGItems.ITEMS.register(name, () -> new BrimsteelBlockItem(registryObject.get(), new Item.Properties().fireResistant()));
		return registryObject;
	}
}
	