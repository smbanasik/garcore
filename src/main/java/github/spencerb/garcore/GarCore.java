package github.spencerb.garcore;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponents;

public class GarCore implements ModInitializer {

	@Override
	public void onInitialize() {
		
		System.out.println("GarCore Engaged!");
		
		
		// TODO Auto-generated method stub
        LootTableEvents.MODIFY.register(
                (key, tableBuilder, source, registries) -> {

                    /*
                     * Ignore loot tables that are not dirt.
                     */
                    if (!key.equals(Blocks.DIRT.getLootTable().orElseThrow())) {
                        return;
                    }
                    
                    HolderGetter<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                    /*
                     * Build a new loot pool.
                     */
                    LootPool.Builder pool = LootPool.lootPool()

                        /*
                         * Generate this pool exactly once.
                         */
                        .setRolls(ConstantValue.exactly(1))

                        /*
                         * Only run if the tool used matches
                         * an amethyst shard.
                         */
                        .when(
                        		MatchTool.toolMatches(
                        				ItemPredicate.Builder.item().of(
                        						itemLookup, Items.AMETHYST_SHARD
                						).withComponents(
                								DataComponentMatchers.Builder.components()
            									.exact(
            										DataComponentExactPredicate.builder()
            										.expect(DataComponents.CUSTOM_NAME, MutableComponent.create(PlainTextContents.create("My Fealty")))
            										.build()
            									).build()
        								)
                				)
                		)

                        /*
                         * Add a dandelion entry.
                         */
                        .add(
                        		LootItem.lootTableItem(Items.DANDELION)
                        			.apply(
                        					SetComponentsFunction.setComponent(DataComponents.ITEM_NAME, 
                        							MutableComponent.create(PlainTextContents.create("Your Association"))
                        								.withColor(ChatFormatting.DARK_RED.getColor())
                							)
                					)
                        );

                    /*
                     * Inject our pool into dirt's loot table.
                     */
                    tableBuilder.withPool(pool);
                }
            );
	}

}
