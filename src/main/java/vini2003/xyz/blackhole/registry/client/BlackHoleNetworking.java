package vini2003.xyz.blackhole.registry.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import vini2003.xyz.blackhole.BlackHole;
import vini2003.xyz.blackhole.common.config.BlackHoleConfig;

public class BlackHoleNetworking {
	public static Identifier PULL_SPEED_PACKET = BlackHole.identifier("pull_speed");
	public static Identifier GROW_SPEED_PACKET = BlackHole.identifier("grow_speed");
	public static Identifier FOLLOW_SPEED_PACKET = BlackHole.identifier("follow_speed");
	public static Identifier PAUSE_PACKET = BlackHole.identifier("pause");
	public static Identifier RESUME_PACKET = BlackHole.identifier("resume");
	
	public static void initialize() {
		ClientPlayNetworking.registerReceiver(PULL_SPEED_PACKET, (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
			BlackHoleConfig.cache.pullSpeed = packetByteBuf.readFloat();
		});
		
		ClientPlayNetworking.registerReceiver(GROW_SPEED_PACKET, (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
			BlackHoleConfig.cache.growSpeed = packetByteBuf.readFloat();
		});
		
		ClientPlayNetworking.registerReceiver(FOLLOW_SPEED_PACKET, (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
			BlackHoleConfig.cache.followSpeed = packetByteBuf.readFloat();
		});
		
		ClientPlayNetworking.registerReceiver(PAUSE_PACKET, (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
			BlackHoleConfig.cache.follow = false;
			BlackHoleConfig.cache.pull = false;
			BlackHoleConfig.cache.grow = false;
		});
		
		ClientPlayNetworking.registerReceiver(RESUME_PACKET, (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
			BlackHoleConfig.cache.follow = true;
			BlackHoleConfig.cache.pull = true;
			BlackHoleConfig.cache.grow = true;
		});
	}
}
