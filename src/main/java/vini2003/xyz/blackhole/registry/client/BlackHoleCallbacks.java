package vini2003.xyz.blackhole.registry.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import vini2003.xyz.blackhole.BlackHoleClient;

public class BlackHoleCallbacks {
	public static void initialize() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			BlackHoleClient.isBlackedOut = false;
		});
	}
}
