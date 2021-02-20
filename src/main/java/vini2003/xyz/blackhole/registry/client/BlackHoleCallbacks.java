package vini2003.xyz.blackhole.registry.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.world.ServerWorld;
import vini2003.xyz.blackhole.BlackHoleClient;
import vini2003.xyz.blackhole.common.components.BlackHoleWorldComponent;
import vini2003.xyz.blackhole.registry.common.BlackHoleComponents;

public class BlackHoleCallbacks {
	public static void initialize() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			BlackHoleClient.isBlackedOut = false;
		});
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (MinecraftClient.getInstance().world != null) {
				BlackHoleWorldComponent blackHoleWorldComponent = BlackHoleComponents.BLACK_HOLES.get(MinecraftClient.getInstance().world);
				
				blackHoleWorldComponent.tick();
			}
		});
	}
}
