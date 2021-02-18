package vini2003.xyz.blackhole.registry.client;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import vini2003.xyz.blackhole.client.entity.render.BlackHoleEntityRenderer;
import vini2003.xyz.blackhole.registry.common.BlackHoleEntityTypes;

public class BlackHoleEntityRenderers {
	public static void initialize() {
		EntityRendererRegistry.INSTANCE.register(BlackHoleEntityTypes.BLACK_HOLE, (manager, context) -> new BlackHoleEntityRenderer(manager));
	}
}
