package github.spencerb.garcore;

import java.util.HashMap;
import java.util.function.BiConsumer;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponents;

// Next I want to make a dispenser that will smelt a log into a dried kelp block if you craft 9 cobblestone and a charcoal together.
// https://docs.fabricmc.net/develop/serialization/saved-data
// Need to look into this.

public class GarCore implements ModInitializer {

	private HashMap<ResourceKey<LootTable>, BiConsumer<LootTable.Builder, HolderLookup.Provider>> lootTableHandlers;

	@Override
	public void onInitialize() {

		System.out.println("GarCore Engaged!");

		lootTableHandlers = new HashMap<ResourceKey<LootTable>, BiConsumer<LootTable.Builder, HolderLookup.Provider>>();

		lootTableHandlers.put(Blocks.DIRT.getLootTable().orElseThrow(), GarCore::injectPactLore);

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

			lootTableHandlers.getOrDefault(key, (_, _) -> {
			}).accept(tableBuilder, registries);

		});
	} // end main

	private static void injectPactLore(LootTable.Builder tableBuilder, HolderLookup.Provider registries) {
		HolderGetter<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
		LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
				.when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(itemLookup, Items.AMETHYST_SHARD)
						.withComponents(DataComponentMatchers.Builder.components()
								.exact(DataComponentExactPredicate.builder()
										.expect(DataComponents.CUSTOM_NAME,
												MutableComponent.create(PlainTextContents.create("My Fealty")))
										.build())
								.build())))
				.add(LootItem.lootTableItem(Items.DANDELION)
						.apply(SetComponentsFunction.setComponent(DataComponents.ITEM_NAME,
								MutableComponent.create(PlainTextContents.create("Your Association"))
										.withColor(ChatFormatting.DARK_RED.getColor()))));

		tableBuilder.withPool(pool);
	}

}
