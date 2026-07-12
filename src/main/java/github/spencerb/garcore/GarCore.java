package github.spencerb.garcore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

public class GarCore implements ModInitializer {
	
	public static final String MOD_ID = "garcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private HashMap<ResourceKey<LootTable>, BiConsumer<LootTable.Builder, HolderLookup.Provider>> lootTableHandlers;
	//private MachineSystem systems;

	@Override
	public void onInitialize() {

		LOGGER.info("GarCore Engaged!");

		lootTableHandlers = new HashMap<ResourceKey<LootTable>, BiConsumer<LootTable.Builder, HolderLookup.Provider>>();
		lootTableHandlers.put(Blocks.DIRT.getLootTable().orElseThrow(), GarCore::injectPactLore);
		lootTableHandlers.put(Blocks.GRASS_BLOCK.getLootTable().orElseThrow(), GarCore::injectPactLore);
		
		//systems = new MachineSystem();
		//TestMachine testMachine = new TestMachine();
		//systems.machines.put(GlobalPos.of(Level.OVERWORLD, BlockPos.containing(0, 70, 0)), testMachine);

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

			lootTableHandlers.getOrDefault(key, (_, _) -> {
			}).accept(tableBuilder, registries);

		});
		
		UseItemCallback.EVENT.register((player, world, hand) -> {
			
			ItemStack handItem = player.getItemInHand(hand);
			
			// TODO: make a map of ItemStack or resource keys (unsure which) that executes a function
			// if it has an entry in the map, instead of if statements.
			if(RitualDagger.isRitualDagger(handItem)) {
				return RitualDagger.ritualDaggerSelf(player, world);
			}

			return InteractionResult.PASS;
		});
		
		
		/*
		ServerTickEvents.END_LEVEL_TICK.register(level -> {
			
			Iterator<HashMap.Entry<GlobalPos, TestMachine>> it =
					systems.machines.entrySet().iterator();
			
			while(it.hasNext()) {
				var entry = it.next();
				GlobalPos machinePos = entry.getKey();
				if(!machinePos.dimension().equals(level.dimension())) {
					continue;
				}
				
				TestMachine machine = entry.getValue();
				machine.tickMachine(level, machinePos.pos(), it);
				
			}
		})
		*/;
		
	} // end main

	private static void injectPactLore(LootTable.Builder tableBuilder, HolderLookup.Provider registries) {
		HolderGetter<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
		LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
				.when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(itemLookup, Items.AMETHYST_SHARD)
						.withComponents(DataComponentMatchers.Builder.components()
								.exact(DataComponentExactPredicate.builder()
										.expect(DataComponents.CUSTOM_NAME,
												MutableComponent.create(PlainTextContents.create("My Will")))
										.build())
								.build())))
				.add(LootItem.lootTableItem(Items.DANDELION)
						.apply(SetComponentsFunction.setComponent(DataComponents.ITEM_NAME,
								MutableComponent.create(PlainTextContents.create("Your Feality"))
										.withColor(ChatFormatting.DARK_RED.getColor()))));

		tableBuilder.withPool(pool);
	}

}
