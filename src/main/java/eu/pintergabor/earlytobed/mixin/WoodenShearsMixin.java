package eu.pintergabor.earlytobed.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static eu.pintergabor.earlytobed.Global.*;

@Mixin(TitleScreen.class)
public class WoodenShearsMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        LOGGER.info("This line is printed by the EarlyToBed mod mixin!");
    }
}
