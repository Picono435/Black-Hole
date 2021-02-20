package vini2003.xyz.blackhole;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import vini2003.xyz.blackhole.registry.client.BlackHoleCallbacks;
import vini2003.xyz.blackhole.registry.client.BlackHoleNetworking;

@Environment(EnvType.CLIENT)
public class BlackHoleClient implements ClientModInitializer {
	public static boolean isBlackedOut = false;
	
	@Override
	public void onInitializeClient() {
		BlackHoleCallbacks.initialize();
		BlackHoleNetworking.initialize();
	}
}
