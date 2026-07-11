package github.spencerb.garcore;

import java.util.Iterator;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;

public class TestMachine {

	int progressTicks;
	
	public static final int CRAFT_SECONDS = 2;
	public static final int CRAFT_TICKS = CRAFT_SECONDS * 20;
	public static final Predicate<ItemStack> ALLOWED_ITEMS = stack -> stack.is(ItemTags.LOGS) 
			|| stack.is(Items.BAMBOO_BLOCK)
			|| stack.is(Items.STRIPPED_BAMBOO_BLOCK);
	
	TestMachine() {
		
	}
	
	public void tickMachine(
			ServerLevel level,
			BlockPos pos,
			Iterator<?> iterator
			) {
		
		if(!level.isLoaded(pos)) {
			iterator.remove();
			return;
		}
		
		DispenserBlockEntity dispenser = level.getBlockEntity(pos, BlockEntityType.DISPENSER).orElse(null);
		
		if(dispenser == null) {
			iterator.remove();
			return;
		}
		
		ItemStack input = dispenser.getItem(0);
		ItemStack output = dispenser.getItem(1);
		
		if(!ALLOWED_ITEMS.test(input) || (!output.isEmpty() && !output.is(Items.CHARCOAL))) {
			this.progressTicks = 0;
			return;
		}
				
		this.progressTicks = this.progressTicks + 1;
		
		if(this.progressTicks < CRAFT_TICKS || output.getCount() >= output.getMaxStackSize()) {
			return;
		}
		
		performOperation(dispenser);
		
		this.progressTicks = 0;
	}
	
	public void performOperation(DispenserBlockEntity dispenser) {
		ItemStack input = dispenser.getItem(0);
		ItemStack output = dispenser.getItem(1);
		
		input.shrink(1);
		
		if(output.isEmpty()) {
			dispenser.setItem(1, new ItemStack(Items.CHARCOAL));
		} else {
			output.grow(1);
		}
		
		dispenser.setChanged();
	}
	
	
}
