package vini2003.xyz.blackhole.registry.client;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import vini2003.xyz.blackhole.common.entity.BlackHoleEntity;
import vini2003.xyz.blackhole.registry.common.BlackHoleEntityTypes;

import java.util.UUID;

public class BlackHolePackets {
	public static void initialize() {
		ClientSidePacketRegistry.INSTANCE.register(BlackHoleEntity.SPAWN_PACKET, (context, buffer) -> {
			double x = buffer.readDouble();
			double y = buffer.readDouble();
			double z = buffer.readDouble();
			int id = buffer.readInt();
			UUID uuid = buffer.readUuid();
			
			context.getTaskQueue().execute(() -> {
				BlackHoleEntity blackHole = new BlackHoleEntity(BlackHoleEntityTypes.BLACK_HOLE, MinecraftClient.getInstance().world);
				blackHole.updateTrackedPosition(x, y, z);
				blackHole.refreshPositionAfterTeleport(x, y, z);
				blackHole.setPos(x, y, z);
				blackHole.setUuid(uuid);
				blackHole.setEntityId(id);
				MinecraftClient.getInstance().world.addEntity(id, blackHole);
			});
		});
	}
}
