package github.spencerb.garcore.chestcraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;

public class CultChestActiveItem {

	public static void onItemActivation(
			ServerLevel level,
			BlockPos pos,
			Container targetContainer) {
		
		CultChestRecipe ourRecipe = CultChestRecipes.find(targetContainer);
		
		if(ourRecipe == null) {
			return;
		}
		
		targetContainer.clearContent();
		targetContainer.setItem(ourRecipe.outputSlot(), ourRecipe.assemble());
		
		level.sendParticles(ParticleTypes.SMOKE, pos.getX(), pos.getY(), pos.getZ(), 20, 0.25, 0.25, 0.25, 0.01);
		level.playSound(null, pos, SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS);
	}
	
}
