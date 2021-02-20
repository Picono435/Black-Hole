package vini2003.xyz.blackhole.registry.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import vini2003.xyz.blackhole.common.components.BlackHoleWorldComponent;

public class BlackHoleCallbacks {
	public static void initialize() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerWorld world : server.getWorlds()) {
				BlackHoleWorldComponent blackHoleWorldComponent = BlackHoleComponents.BLACK_HOLES.get(world);
				
				blackHoleWorldComponent.tick();
			}
		});
	}
}
