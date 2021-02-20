package vini2003.xyz.blackhole.registry.common;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import vini2003.xyz.blackhole.BlackHole;
import vini2003.xyz.blackhole.common.components.BlackHoleWorldComponent;

public class BlackHoleComponents {
	public static final ComponentKey<BlackHoleWorldComponent> BLACK_HOLES = ComponentRegistry.getOrCreate(BlackHole.identifier("black_holes"), BlackHoleWorldComponent.class);
	
	public static void initialize() {
	
	}
}
