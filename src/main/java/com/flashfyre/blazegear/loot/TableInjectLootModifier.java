package com.flashfyre.blazegear.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class TableInjectLootModifier extends LootModifier {
	
	public static final Codec<TableInjectLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance).and(
			ResourceLocation.CODEC.fieldOf("table").forGetter(lootModifier -> lootModifier.tableToInject)).apply(instance, TableInjectLootModifier::new));
	
	private final ResourceLocation tableToInject;

	protected TableInjectLootModifier(LootItemCondition[] conditionsIn, ResourceLocation tableToInject) {
		super(conditionsIn);
		this.tableToInject = tableToInject;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext ctx) {
		LootTable tableToInject = ctx.getResolver().getLootTable(this.tableToInject);
		tableToInject.getRandomItemsRaw(ctx, LootTable.createStackSplitter(ctx.getLevel(), generatedLoot::add));
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
}