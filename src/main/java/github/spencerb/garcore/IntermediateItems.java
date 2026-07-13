package github.spencerb.garcore;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class IntermediateItems {

	public static ItemStack getBlood() {
		ItemStack blood = new ItemStack(Items.RED_DYE);
		
		blood.set(DataComponents.ITEM_NAME, MutableComponent.create(PlainTextContents.create("Blood"))
				.withColor(ChatFormatting.DARK_RED.getColor()));
		
		return blood;
	}
	
	public static ItemStack getImpureIchor() {
		ItemStack ichor = new ItemStack(Items.ORANGE_DYE);
		
		ichor.set(DataComponents.ITEM_NAME, MutableComponent.create(PlainTextContents.create("Impure Ichor"))
				.withColor(ChatFormatting.GOLD.getColor()));
		
		return ichor;
	}
	
	public static ItemStack getPureIchor() {
		ItemStack ichor = new ItemStack(Items.YELLOW_DYE);
		
		ichor.set(DataComponents.ITEM_NAME, MutableComponent.create(PlainTextContents.create("Pure Ichor"))
				.withColor(ChatFormatting.YELLOW.getColor()));
		
		return ichor;
	}
	
	public static ItemStack getBloodClay() {
		ItemStack clay = new ItemStack(Items.CLAY);
		
		clay.set(DataComponents.ITEM_NAME, MutableComponent.create(PlainTextContents.create("Corpse Dirt"))
				.withColor(ChatFormatting.DARK_RED.getColor()));
		
		return clay;
	}
}
