package github.spencerb.garcore.chestcraft;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record SlotKey (
		Holder<Item> item,
		int count,
		DataComponentPatch components
		) {

	public static final SlotKey EMPTY = new SlotKey(null, 0, DataComponentPatch.EMPTY);
	
	public SlotKey(Holder<Item> newItem) {
		this(newItem, 1, DataComponentPatch.EMPTY);
	}
	
	public SlotKey(Holder<Item> newItem, int count) {
		this(newItem, count, DataComponentPatch.EMPTY);
	}
	
	public SlotKey(Holder<Item> newItem, DataComponentPatch components) {
		this(newItem, 1, components);
	}
	
	public static SlotKey of(Holder<Item> item, int count, DataComponentPatch components) {
		return new SlotKey(item, count, components);
	}
	
	// Deprecating because I'm too lazy to change the RecipeKey implementation
	// But this now uses the deprecated function so don't use it anywhere else.
	@Deprecated
	public static SlotKey from(ItemStack stack) {
		if(stack.isEmpty()) {
			return EMPTY;
		}
		
		return new SlotKey(stack.getItem().builtInRegistryHolder(), stack.getCount(), stack.getComponentsPatch());
	}
	
	public ItemStack toItemStack() {
		
		return new ItemStack(this.item, this.count, this.components);
	}
}
