package vini2003.xyz.blackhole.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vini2003.xyz.blackhole.BlackHoleClient;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Inject(at = @At("HEAD"), method = "render")
	void blackhole_onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		if (BlackHoleClient.isBlackedOut) {
			Window window = MinecraftClient.getInstance().getWindow();
			DrawableHelper.fill(matrices, 0, 0, window.getScaledWidth(), window.getScaledHeight(), 0xFF000000);
		}
	}
}
