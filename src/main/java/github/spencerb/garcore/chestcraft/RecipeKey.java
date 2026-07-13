package github.spencerb.garcore.chestcraft;

import java.util.Arrays;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;

public class RecipeKey {

	private static final int SIZE = 27;
	
	private final SlotKey[] slots;
	private final int hash;
	
	public RecipeKey(SlotKey[] slots) {
		if (slots.length != SIZE) {
			throw new IllegalArgumentException(
                    "Chest recipes require exactly 27 slots.");
		}
		
		this.slots = slots.clone();
		this.hash = Arrays.hashCode(this.slots);
	}
	
    public static RecipeKey from(Container container) {
        SlotKey[] slots = new SlotKey[SIZE];

        for (int i = 0; i < SIZE; i++) {
            slots[i] = SlotKey.from(
                    container.getItem(i));
        }

        return new RecipeKey(slots);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || obj instanceof RecipeKey other
                && Arrays.equals(
                        slots,
                        other.slots);
    }

    @Override
    public int hashCode() {
        return hash;
    }
    
    public static class Builder {
    	
    	private final SlotKey[] slots =
                new SlotKey[27];
    	
    	private final RecipeContext ctx;

        public Builder(RecipeContext context) {
            Arrays.fill(
                    slots,
                    SlotKey.EMPTY);
            ctx = context;
        }
        
        public static Builder item(RecipeContext context) {
        	return new Builder(context);
        }
        
        public Builder slot(int index, SlotKey key) {
        	slots[index] = key;
        	return this;
        }

        public Builder slot(
                int index,
                Item item,
                int count,
                DataComponentPatch components) {

            slots[index] =
            		ctx.slot(item, count, components);

            return this;
        }
        
        public Builder slot(int index, Item item, int count) {
        	
        	return this.slot(index, item, count, DataComponentPatch.EMPTY);
        }
        
        public Builder slot(int index, Item item) {
        	return this.slot(index, item, 1, DataComponentPatch.EMPTY);
        }
        
        public Builder fill(Item item, int count, DataComponentPatch components) {
        	Arrays.fill(
        			slots,
        			ctx.slot(item, count, components)
        			);
        	return this;
        }
        
        // Start inclusive, end exclusive
        public Builder fillRange(int start, int end, Item item, int count, DataComponentPatch components) {
        	Arrays.fill(
        			slots,
        			start,
        			end,
        			ctx.slot(item, count, components)
        			);
        	return this;
        }
        
        public Builder clear(int index) {
        	slots[index] = SlotKey.EMPTY;
        	return this;
        }

        public RecipeKey build() {
            return new RecipeKey(slots);
        }
    }
}
