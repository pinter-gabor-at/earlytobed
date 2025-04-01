package eu.pintergabor.earlytobed.mixin;


import static eu.pintergabor.earlytobed.item.ModItems.WOODEN_SHEARS_ITEM;
import static net.minecraft.world.entity.LivingEntity.getSlotForHand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;


/**
 * Inject code into SheepEntity#interactMob
 * <p>
 * The code is identical to the original one,
 * but checks for WOODEN_SHEARS_ITEM instead of SHEARS.
 */
@Mixin(Sheep.class)
public abstract class WoodenShearsMixin {

	@Inject(at = @At("HEAD"),
		method = "mobInteract",
		cancellable = true)
	private void mobInteract(
		Player player, InteractionHand hand,
		CallbackInfoReturnable<InteractionResult> cir) {
		Sheep that = (Sheep) (Object) this;
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(WOODEN_SHEARS_ITEM)) {
			if (that.level() instanceof ServerLevel serverLevel && that.readyForShearing()) {
				that.shear(serverLevel, SoundSource.PLAYERS, itemStack);
				that.gameEvent(GameEvent.SHEAR, player);
				itemStack.hurtAndBreak(1, player, getSlotForHand(hand));
				cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
			} else {
				cir.setReturnValue(InteractionResult.CONSUME);
			}
		}
	}
}
