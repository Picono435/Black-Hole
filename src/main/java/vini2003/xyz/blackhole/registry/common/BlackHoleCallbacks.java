package vini2003.xyz.blackhole.registry.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;
import vini2003.xyz.blackhole.common.entity.BlackHoleEntity;

public class BlackHoleCallbacks {
	public static void initialize() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerWorld world : server.getWorlds()) {
				for (Entity entity : world.getEntitiesByType(BlackHoleEntityTypes.BLACK_HOLE, (entity) -> true)) {
					BlackHoleEntity blackHole = (BlackHoleEntity) entity;
					
					if (world.getPlayers(player -> player.distanceTo(entity) < 128F).isEmpty()) {
						blackHole.tick();
						++blackHole.age;
					}
					
					if (blackHole != null) {
						WorldChunk centerChunk = (WorldChunk) world.getChunk(blackHole.getBlockPos());
						
						if (centerChunk != null) {
							for (int x = centerChunk.getPos().x - 7; x < centerChunk.getPos().x + 7; ++x) {
								for (int z = centerChunk.getPos().z - 7; z < centerChunk.getPos().z + 7; ++z) {
									WorldChunk chunk = world.getChunk(x, z);
									
									if (chunk != null) {
										chunk.setLoadedToWorld(true);
									}
								}
							}
						}
					}
				}
			}
		});
	}
}
