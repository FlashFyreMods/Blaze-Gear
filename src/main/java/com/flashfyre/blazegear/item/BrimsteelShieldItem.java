package com.flashfyre.blazegear.item;

import com.flashfyre.blazegear.BGUtil;
import com.flashfyre.blazegear.registry.BGAttributes;
import com.flashfyre.blazegear.registry.BGItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrimsteelShieldItem extends ShieldItem {
    public BrimsteelShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack thisStack, ItemStack stack) {
        return stack.is(BGItems.BRIMSTEEL_INGOT.get());
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return super.getDescriptionId();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.MAINHAND && slot != EquipmentSlot.OFFHAND)
            return super.getAttributeModifiers(slot, stack);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<Attribute, AttributeModifier>();
        AttributeModifier attackerBurnTimeMod = new AttributeModifier(BGUtil.SHIELD_BURN_TIME_UUID, "Shield attacker burn time", 4F, AttributeModifier.Operation.ADDITION);
        builder.put(BGAttributes.BLOCKED_ATTACKER_BURN_TIME.get(), attackerBurnTimeMod);
        builder.putAll(super.getAttributeModifiers(slot, stack));
        return builder.build();
    }
}
