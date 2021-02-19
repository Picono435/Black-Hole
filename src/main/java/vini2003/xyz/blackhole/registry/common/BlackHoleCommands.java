package vini2003.xyz.blackhole.registry.common;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vini2003.xyz.blackhole.common.config.BlackHoleConfig;
import vini2003.xyz.blackhole.common.entity.BlackHoleEntity;

public class BlackHoleCommands {
	private static int spawnBlackHole(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlockPos blockPos = context.getSource().getPlayer().getBlockPos();
		
		World world = context.getSource().getWorld();
		
		BlackHoleEntity blackHoleEntity = new BlackHoleEntity(BlackHoleEntityTypes.BLACK_HOLE, world);
		blackHoleEntity.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		blackHoleEntity.requestTeleport(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		
		world.spawnEntity(blackHoleEntity);
		
		return 1;
	}
	
	private static int growSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.growthSpeed = IntegerArgumentType.getInteger(context, "growSpeed") * BlackHoleConfig.defaultGrow;
		
		return 1;
	}
	
	private static int pullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pullSpeed = IntegerArgumentType.getInteger(context, "pullSpeed") * BlackHoleConfig.defaultPull;
		
		return 1;
	}
	
	private static int followSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.followSpeed = IntegerArgumentType.getInteger(context, "followSpeed") * BlackHoleConfig.defaultFollow;
		
		return 1;
	}
	
	private static int pause(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = false;
		BlackHoleConfig.cache.pull = false;
		BlackHoleConfig.cache.grow = false;
		
		return 1;
	}
	
	private static int resume(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.grow = true;
		
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
									CommandManager.argument("pullSpeed", FloatArgumentType.floatArg())
											.executes(BlackHoleCommands::pullSpeed)
											.build()
							).build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrow =
					CommandManager.literal("grow")
							.then(
									CommandManager.argument("growSpeed", FloatArgumentType.floatArg())
											.executes(BlackHoleCommands::growSpeed)
											.build()
							).build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollow =
					CommandManager.literal("follow")
							.then(
									CommandManager.argument("followSpeed", FloatArgumentType.floatArg())
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
			
			dispatcher.getRoot().addChild(blackHoleRoot);
		});
	}
}
