package vini2003.xyz.blackhole;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import vini2003.xyz.blackhole.registry.common.*;

public class BlackHole implements ModInitializer {
	public static final String ID = "blackhole";
	
	public static Identifier identifier(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		BlackHoleCommands.initialize();
		BlackHoleCallbacks.initialize();
		BlackHoleConfigs.initialize();
		BlackHoleComponents.initialize();
	}
}
