package github.spencerb.garcore;

import github.spencerb.garcore.chestcraft.CultChestActiveItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MyWill {
	
	public static boolean isMyWill(ItemStack item) {

		if(!item.is(Items.AMETHYST_SHARD)) {
			return false;
		}
		
		Component name = item.get(DataComponents.CUSTOM_NAME);
		if(name == null) {
			return false;
		}
		
		return "My Will".equals(name.getString());
	}
	
	
	public static InteractionResult myWillInteraction(Player player, Level level, BlockHitResult hitResult) {
		
		BlockPos pos = hitResult.getBlockPos();
		BlockEntity be = level.getBlockEntity(pos);
		
		if (be instanceof ChestBlockEntity chest && level instanceof ServerLevel serverLevel) {
			
			CultChestActiveItem.onItemActivation(serverLevel, pos, chest);
			return InteractionResult.SUCCESS;
		}
		
		return InteractionResult.PASS;
	}

}
