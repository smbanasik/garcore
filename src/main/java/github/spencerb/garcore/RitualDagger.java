package github.spencerb.garcore;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RitualDagger {
	
	final public static float DAGGER_DAMAGE = 4.0F;
	
	public static boolean isRitualDagger(ItemStack item) {

		if(!item.is(Items.GOLDEN_SWORD)) {
			return false;
		}
		
		Component name = item.get(DataComponents.CUSTOM_NAME);
		if(name == null) {
			return false;
		}
		
		return "Ritual Dagger".equals(name.getString());
	}
	
	public static InteractionResult ritualDaggerSelf(Player player, Level level) {
		
		if(level instanceof ServerLevel serverLevel) {
			player.hurtServer(serverLevel, level.damageSources().magic(), DAGGER_DAMAGE);
			
			player.spawnAtLocation(serverLevel, IntermediateItems.getBlood());
			
			return InteractionResult.SUCCESS;
		}
		
		return InteractionResult.PASS;
	}

}
