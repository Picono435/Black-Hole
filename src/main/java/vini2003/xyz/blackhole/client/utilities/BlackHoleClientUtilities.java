package vini2003.xyz.blackhole.client.utilities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class BlackHoleClientUtilities {
	public static PlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
	}
}
