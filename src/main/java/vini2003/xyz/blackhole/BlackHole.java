package vini2003.xyz.blackhole;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import vini2003.xyz.blackhole.registry.common.BlackHoleCallbacks;
import vini2003.xyz.blackhole.registry.common.BlackHoleCommands;
import vini2003.xyz.blackhole.registry.common.BlackHoleConfigs;
import vini2003.xyz.blackhole.registry.common.BlackHoleDamageSources;

public class BlackHole implements ModInitializer {
	public static final String ID = "blackhole";
	
	public static Identifier identifier(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		BlackHoleCommands.initialize();
		BlackHoleDamageSources.initialize();
		BlackHoleCallbacks.initialize();
		BlackHoleConfigs.initialize();
	}
}
