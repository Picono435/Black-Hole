package vini2003.xyz.blackhole.registry.common;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vini2003.xyz.blackhole.common.components.BlackHoleComponent;
import vini2003.xyz.blackhole.common.config.BlackHoleConfig;
import vini2003.xyz.blackhole.registry.client.BlackHoleNetworking;

public class BlackHoleCommands {
	private static int spawnBlackHole(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		Vec3d pos = context.getSource().getPlayer().getPos();
		
		World world = context.getSource().getWorld();
		
		BlackHoleComponent blackHole = new BlackHoleComponent(world);
		blackHole.setPos(pos);
		blackHole.setSize(1F);
		
		BlackHoleComponents.BLACK_HOLES.get(world).getBlackHoles().add(blackHole);
		BlackHoleComponents.BLACK_HOLES.sync(world);
		
		return 1;
	}
	
	private static int growSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		float growSpeed = IntegerArgumentType.getInteger(context, "growSpeed") * BlackHoleConfig.defaultGrow;
		
		BlackHoleConfig.cache.growSpeed = growSpeed;
		
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeFloat(growSpeed);
		
		ServerPlayNetworking.send(context.getSource().getPlayer(), BlackHoleNetworking.GROW_SPEED_PACKET, buf);
		
		return 1;
	}
	
	private static int pullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		float pullSpeed = IntegerArgumentType.getInteger(context, "pullSpeed") * BlackHoleConfig.defaultGrow;
		
		BlackHoleConfig.cache.pullSpeed = pullSpeed;
		
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeFloat(pullSpeed);
		
		ServerPlayNetworking.send(context.getSource().getPlayer(), BlackHoleNetworking.PULL_SPEED_PACKET, buf);
		
		return 1;
	}
	
	private static int followSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		float followSpeed = IntegerArgumentType.getInteger(context, "followSpeed") * BlackHoleConfig.defaultGrow;
		
		BlackHoleConfig.cache.followSpeed = followSpeed;
		
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeFloat(followSpeed);
		
		ServerPlayNetworking.send(context.getSource().getPlayer(), BlackHoleNetworking.FOLLOW_SPEED_PACKET, buf);
		
		return 1;
	}
	
	private static int pause(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = false;
		BlackHoleConfig.cache.pull = false;
		BlackHoleConfig.cache.grow = false;
		
		ServerPlayNetworking.send(context.getSource().getPlayer(), BlackHoleNetworking.PAUSE_PACKET, new PacketByteBuf(Unpooled.buffer()));
		
		return 1;
	}
	
	private static int resume(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.grow = true;
		
		ServerPlayNetworking.send(context.getSource().getPlayer(), BlackHoleNetworking.RESUME_PACKET, new PacketByteBuf(Unpooled.buffer()));
		
		return 1;
	}
	
	public static void initialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			LiteralCommandNode<ServerCommandSource> blackHoleRoot = CommandManager.literal("blackhole").build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleSpawn =
					CommandManager.literal("spawn")
							.executes(BlackHoleCommands::spawnBlackHole)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePull =
					CommandManager.literal("pull")
							.then(
									CommandManager.argument("pullSpeed", IntegerArgumentType.integer())
											.executes(BlackHoleCommands::pullSpeed)
											.build()
							).build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrow =
					CommandManager.literal("grow")
							.then(
									CommandManager.argument("growSpeed", IntegerArgumentType.integer())
											.executes(BlackHoleCommands::growSpeed)
											.build()
							).build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollow =
					CommandManager.literal("follow")
							.then(
									CommandManager.argument("followSpeed", IntegerArgumentType.integer())
											.executes(BlackHoleCommands::followSpeed)
											.build()
							).build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePause =
					CommandManager.literal("pause")
							.executes(BlackHoleCommands::pause)
					.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleResume =
					CommandManager.literal("resume")
							.executes(BlackHoleCommands::resume)
					.build();
			
			
			blackHoleRoot.addChild(blackHoleSpawn);
			blackHoleRoot.addChild(blackHolePull);
			blackHoleRoot.addChild(blackHoleGrow);
			blackHoleRoot.addChild(blackHoleFollow);
			blackHoleRoot.addChild(blackHolePause);
			blackHoleRoot.addChild(blackHoleResume);
			
			dispatcher.getRoot().addChild(blackHoleRoot);
		});
	}
}
