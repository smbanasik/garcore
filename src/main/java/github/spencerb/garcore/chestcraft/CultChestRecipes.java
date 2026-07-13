package github.spencerb.garcore.chestcraft;

import java.util.HashMap;
import java.util.Map;

import github.spencerb.garcore.IntermediateItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class CultChestRecipes {
	
	private static final Map<RecipeKey, CultChestRecipe> RECIPES = new HashMap<>();
	
	private CultChestRecipes() {}
	
	public static void Register(RegistryAccess registries) {
			
		RecipeContext ctx = new RecipeContext(registries);
		
		SlotKey blood = ctx.from(IntermediateItems.getBlood());
		SlotKey impureIchor = ctx.from(IntermediateItems.getImpureIchor());
		SlotKey pureIchor = ctx.from(IntermediateItems.getPureIchor());
		SlotKey bloodClay = ctx.from(IntermediateItems.getBloodClay());
		
		CultChestRecipes.Recipe(
				new CultChestRecipe(RecipeKey.Builder.item(ctx)
						.slot(2, blood)
						.slot(4, Items.COPPER_BLOCK)
						.slot(6, blood)
						.slot(10, blood)
						.slot(12, blood)
						.slot(14, blood)
						.slot(16, blood)
						.slot(18, blood)
						.slot(20, Items.COPPER_BLOCK)
						.slot(22, blood)
						.slot(24, Items.COPPER_BLOCK)
						.slot(26, blood)
						.build(),
						impureIchor)
				);
		
		CultChestRecipes.Recipe(
				new CultChestRecipe(RecipeKey.Builder.item(ctx)
						.slot(2, impureIchor)
						.slot(4, Items.GOLD_BLOCK)
						.slot(6, impureIchor)
						.slot(10, impureIchor)
						.slot(12, impureIchor)
						.slot(14, impureIchor)
						.slot(16, impureIchor)
						.slot(18, impureIchor)
						.slot(20, Items.GOLD_BLOCK)
						.slot(22, impureIchor)
						.slot(24, Items.GOLD_BLOCK)
						.slot(26, impureIchor)
						.build(),
						pureIchor)
				);
		
		CultChestRecipes.Recipe(
				new CultChestRecipe(RecipeKey.Builder.item(ctx)
						.slot(0, blood.item().value(), 32, blood.components())
						.slot(1, Items.BONE_MEAL, 32)
						.slot(2, Items.COBBLESTONE, 64)
						.build(),
						ctx.slot(Items.CALCITE))
				);
		
		CultChestRecipes.Recipe(
				new CultChestRecipe(RecipeKey.Builder.item(ctx)
						.slot(0, blood.item().value(), 32, blood.components())
						.slot(1, Items.BONE_MEAL, 32)
						.slot(2, Items.COBBLESTONE, 64)
						.build(),
						ctx.slot(Items.CALCITE, 64))
				);
		
		CultChestRecipes.Recipe(
				new CultChestRecipe(RecipeKey.Builder.item(ctx)
						.fillRange(0, 3, blood.item().value(), 1, blood.components())
						.slot(3, Items.BONE)
						.slot(4, Items.BONE_BLOCK)
						.slot(5, Items.BONE)
						.fillRange(6, 9, blood.item().value(), 1, blood.components())
						.slot(9, blood)
						.slot(10, Items.SLIME_BALL)
						.slot(11, blood)
						.slot(12, Items.BONE)
						.slot(14, Items.BONE)
						.slot(15, blood)
						.slot(16, Items.SLIME_BALL)
						.slot(17, blood)
						.fillRange(18, 21, blood.item().value(), 1, blood.components())
						.slot(21, Items.BONE_BLOCK)
						.slot(22, Items.BONE)
						.slot(23, Items.BONE_BLOCK)
						.fillRange(24, 27, blood.item().value(), 1, blood.components())
						.build(),
						ctx.slot(Items.SKELETON_SKULL))
				);
		
		CultChestRecipes.Recipe(
				new CultChestRecipe(RecipeKey.Builder.item(ctx)
						.slot(0, blood.item().value(), 32, blood.components())
						.slot(1, Items.ROTTEN_FLESH, 32)
						.slot(2, Items.DIRT, 32)
						.build(),
						ctx.slot(bloodClay.item().value(), 32, bloodClay.components()))
				);
		
		CultChestRecipes.Recipe(
				new CultChestRecipe(RecipeKey.Builder.item(ctx)
						.slot(2, bloodClay)
						.fillRange(3, 6, Items.ARMADILLO_SCUTE, 1, DataComponentPatch.EMPTY)
						.slot(6, bloodClay)
						.slot(10, impureIchor)
						.slot(11, bloodClay)
						.slot(12, Items.ARMADILLO_SCUTE)
						.slot(13, Items.EGG)
						.slot(14, Items.ARMADILLO_SCUTE)
						.slot(15, bloodClay)
						.slot(16, impureIchor)
						.slot(20, bloodClay)
						.fillRange(21, 24, Items.ARMADILLO_SCUTE, 1, DataComponentPatch.EMPTY)
						.slot(24, bloodClay)
						.build(),
						ctx.slot(Items.TURTLE_EGG))
				);
		
		CultChestRecipes.Recipe(
				new CultChestRecipe(RecipeKey.Builder.item(ctx)
						.fillRange(3, 6, blood.item().value(), 1, blood.components())
						.slot(12, blood)
						.slot(14, blood)
						.fillRange(21, 24, blood.item().value(), 1, blood.components())
						.build(),
						ctx.slot(Items.REDSTONE, 4))
				);
	}
	
	private static void Recipe(CultChestRecipe recipe) {
		RECIPES.put(recipe.input(), recipe);
	}
	
	public static CultChestRecipe find(
			Container container) {
		return RECIPES.get(RecipeKey.from(container));
	}
}
