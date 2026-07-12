package github.spencerb.garcore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import github.spencerb.garcore.RitualDagger;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(
			method = "hurtServer",
			at = @At("RETURN")
	)
	private void ritualDaggerMixin(
		ServerLevel level,
		DamageSource source,
        float amount,
        CallbackInfoReturnable<Boolean> cir) {
		
		if(!cir.getReturnValue()) {
			return;
		}
		
		Entity attacker = source.getEntity();
		if(!(attacker instanceof Player player)) {
			return;
		}
		
		ItemStack weapon = player.getItemInHand(player.getUsedItemHand());
		
		if(!RitualDagger.isRitualDagger(weapon)) {
			return;
		}
		
		LivingEntity victim = (LivingEntity)(Object)this;
		victim.spawnAtLocation(level, RitualDagger.getBlood());
	}
	
}
