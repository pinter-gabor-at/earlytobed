package eu.pintergabor.earlytobed.mixin;

import static eu.pintergabor.earlytobed.item.ModItems.WOODEN_SHEARS_ITEM;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.event.GameEvent;

/**
 * Inject code into SheepEntity#interactMob The code is identical to the original one, but checks for WOODEN_SHEARS_ITEM
 * instead of SHEARS
 */
@Mixin(SheepEntity.class)
public abstract class WoodenShearsMixin {
	@Inject(at = @At("HEAD"),
		method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
		cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		SheepEntity that = (SheepEntity) (Object) this;
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(WOODEN_SHEARS_ITEM)) {
			if (!that.getWorld().isClient && that.isShearable()) {
				that.sheared(SoundCategory.PLAYERS);
				that.emitGameEvent(GameEvent.SHEAR, player);
				itemStack.damage(1, player, playerx -> playerx.sendToolBreakStatus(hand));
				cir.setReturnValue(ActionResult.SUCCESS);
			} else {
				cir.setReturnValue(ActionResult.CONSUME);
			}
		}
	}
}
