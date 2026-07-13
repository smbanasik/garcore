package github.spencerb.garcore.chestcraft;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// I AM LEARNING SO MUCH ABOUT JAVA!
// For real though, I should incorporate this pattern into my game engine instead of using
// Static variables to establish preconditions
public class RecipeContext {

	private final HolderGetter<Item> items;
	
	public RecipeContext(RegistryAccess registries) {
		this.items = registries.lookupOrThrow(Registries.ITEM);
	}
	
	public SlotKey slot(Item item, int count, DataComponentPatch components) {
		// I cannot find an elegant way to do what I want without this deprecated method.
		// I don't understand what I'm supposed to use as an alternative, everything else looks incredibly
		// ugly.
		Holder<Item> holder = this.items.getOrThrow(item.builtInRegistryHolder().key());
		
		return new SlotKey(holder, count, components);
	}
	
	public SlotKey slot(Item item) {
		return slot(item, 1, DataComponentPatch.EMPTY);
	}
	
	public SlotKey slot (Item item, int count) {
		return slot(item, count, DataComponentPatch.EMPTY);
	}
	
	public SlotKey slot (Item item, DataComponentPatch components) {
		return slot(item, 1, components);
	}
	
	public SlotKey from(ItemStack stack) {
		return slot(stack.getItem(), stack.getCount(), stack.getComponentsPatch());
	}
	
}
