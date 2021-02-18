package vini2003.xyz.blackhole.registry.common;

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
	
	private static int offGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = false;
		
		return 1;
	}
	
	private static int slowestGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = true;
		BlackHoleConfig.cache.growthSpeed = 0.00003851D / 100D;
		
		return 1;
	}
	
	private static int slowerGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = true;
		BlackHoleConfig.cache.growthSpeed = 0.00003851D / 25D;
		
		return 1;
	}
	
	private static int slowGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = true;
		BlackHoleConfig.cache.growthSpeed = 0.00003851D / 6.25D;
		
		return 1;
	}
	
	private static int standardGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = true;
		BlackHoleConfig.cache.growthSpeed = 0.00003851D;
		
		return 1;
	}
	
	private static int fastGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = true;
		BlackHoleConfig.cache.growthSpeed = 0.00003851D * 6.25D;
		
		return 1;
	}
	
	private static int fasterGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = true;
		BlackHoleConfig.cache.growthSpeed = 0.00003851D * 25D;
		
		return 1;
	}
	
	private static int fastestGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = true;
		BlackHoleConfig.cache.growthSpeed = 0.00003851D * 100D;
		
		return 1;
	}
	
	private static int onGrowthSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.grow = true;
		
		return 1;
	}
	
	private static int offFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = false;
		
		return 1;
	}
	
	private static int slowestFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.followSpeed = 0.00125D / 10D;
		
		return 1;
	}
	
	private static int slowerFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.followSpeed = 0.00125D / 5D;
		
		return 1;
	}
	
	private static int slowFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.followSpeed = 0.00125D / 2.5D;
		
		return 1;
	}
	
	private static int standardFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.followSpeed = 0.00125D;
		
		return 1;
	}
	
	private static int fastFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.followSpeed = 0.00125D * 2.5D;
		
		return 1;
	}
	
	private static int fasterFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.followSpeed = 0.00125D * 5D;
		
		return 1;
	}
	
	private static int fastestFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.followSpeed = 0.00125D * 10D;
		
		return 1;
	}
	
	private static int onFollowSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.follow = true;
		
		return 1;
	}
	
	private static int offPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = false;
		
		return 1;
	}
	
	private static int slowestPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.pullSpeed = 1.25D / 10D;
		
		return 1;
	}
	
	private static int slowerPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.pullSpeed = 1.25D / 5D;
		
		return 1;
	}
	
	private static int slowPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.pullSpeed = 1.25D / 2.5D;
		
		return 1;
	}
	
	private static int standardPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.pullSpeed = 1.25D;
		
		return 1;
	}
	
	private static int fastPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.pullSpeed = 1.25D * 2.5D;
		
		return 1;
	}
	
	private static int fasterPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.pullSpeed = 1.25D * 5D;
		
		return 1;
	}
	
	private static int fastestPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = true;
		BlackHoleConfig.cache.pullSpeed = 1.25D * 10D;
		
		return 1;
	}
	
	private static int onPullSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.pull = true;
		
		return 1;
	}
	
	private static int offDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = false;
		
		return 1;
	}
	
	private static int weakestDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		BlackHoleConfig.cache.damageStrength = 2.5D / 10D;
		
		return 1;
	}
	
	private static int weakerDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		BlackHoleConfig.cache.damageStrength = 2.5D / 5D;
		
		return 1;
	}
	
	private static int weakDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		BlackHoleConfig.cache.damageStrength = 2.5D / 2.5D;
		
		return 1;
	}
	
	private static int standardDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		BlackHoleConfig.cache.damageStrength = 2.5D;
		
		return 1;
	}
	
	private static int strongDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		BlackHoleConfig.cache.damageStrength = 2.5D * 2.5D;
		
		return 1;
	}
	
	private static int strongerDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		BlackHoleConfig.cache.damageStrength = 2.5D * 5D;
		
		return 1;
	}
	
	private static int strongestDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		BlackHoleConfig.cache.damageStrength = 2.5D * 10D;
		
		return 1;
	}
	
	private static int onDamageStrength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		
		return 1;
	}
	
	private static int onPause(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = false;
		BlackHoleConfig.cache.grow = false;
		BlackHoleConfig.cache.follow = false;
		BlackHoleConfig.cache.pull = false;
		
		return 1;
	}
	
	private static int onResume(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.damage = true;
		BlackHoleConfig.cache.grow = true;
		BlackHoleConfig.cache.follow = true;
		BlackHoleConfig.cache.pull = true;
		
		return 1;
	}
	
	private static int onLimit(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlackHoleConfig.cache.limit = IntegerArgumentType.getInteger(context, "limit");
		
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
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrow =
					CommandManager.literal("grow")
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowOff =
					CommandManager.literal("off")
							.executes(BlackHoleCommands::offGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowSlowest =
					CommandManager.literal("slowest")
							.executes(BlackHoleCommands::slowestGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowSlower =
					CommandManager.literal("slower")
							.executes(BlackHoleCommands::slowerGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowSlow =
					CommandManager.literal("slow")
							.executes(BlackHoleCommands::slowGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowStandard =
					CommandManager.literal("standard")
							.executes(BlackHoleCommands::standardGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowFast =
					CommandManager.literal("fast")
							.executes(BlackHoleCommands::fastGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowFaster =
					CommandManager.literal("faster")
							.executes(BlackHoleCommands::fasterGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowFastest =
					CommandManager.literal("fastest")
							.executes(BlackHoleCommands::fastestGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleGrowOn =
					CommandManager.literal("on")
							.executes(BlackHoleCommands::onGrowthSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollow =
					CommandManager.literal("follow")
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowOff =
					CommandManager.literal("off")
							.executes(BlackHoleCommands::offFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowSlowest =
					CommandManager.literal("slowest")
							.executes(BlackHoleCommands::slowestFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowSlower =
					CommandManager.literal("slower")
							.executes(BlackHoleCommands::slowerFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowSlow =
					CommandManager.literal("slow")
							.executes(BlackHoleCommands::slowFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowStandard =
					CommandManager.literal("standard")
							.executes(BlackHoleCommands::standardFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowFast =
					CommandManager.literal("fast")
							.executes(BlackHoleCommands::fastFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowFaster =
					CommandManager.literal("faster")
							.executes(BlackHoleCommands::fasterFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowFastest =
					CommandManager.literal("fastest")
							.executes(BlackHoleCommands::fastestFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleFollowOn =
					CommandManager.literal("on")
							.executes(BlackHoleCommands::onFollowSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePull =
					CommandManager.literal("pull")
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePullOff =
					CommandManager.literal("off")
							.executes(BlackHoleCommands::offPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePullSlowest =
					CommandManager.literal("slowest")
							.executes(BlackHoleCommands::slowestPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePullSlower =
					CommandManager.literal("slower")
							.executes(BlackHoleCommands::slowerPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHolePullSlow =
					CommandManager.literal("slow")
							.executes(BlackHoleCommands::slowPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHolePullStandard =
					CommandManager.literal("standard")
							.executes(BlackHoleCommands::standardPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHolePullFast =
					CommandManager.literal("fast")
							.executes(BlackHoleCommands::fastPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePullFaster =
					CommandManager.literal("faster")
							.executes(BlackHoleCommands::fasterPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePullFastest =
					CommandManager.literal("fastest")
							.executes(BlackHoleCommands::fastestPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePullOn =
					CommandManager.literal("on")
							.executes(BlackHoleCommands::onPullSpeed)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamage =
					CommandManager.literal("damage")
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageOff =
					CommandManager.literal("off")
							.executes(BlackHoleCommands::offDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageWeakest =
					CommandManager.literal("weakest")
							.executes(BlackHoleCommands::weakestDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageWeaker =
					CommandManager.literal("weaker")
							.executes(BlackHoleCommands::weakerDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageWeak =
					CommandManager.literal("weak")
							.executes(BlackHoleCommands::weakDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageStandard =
					CommandManager.literal("standard")
							.executes(BlackHoleCommands::standardDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageStrong =
					CommandManager.literal("strong")
							.executes(BlackHoleCommands::strongDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageStronger =
					CommandManager.literal("stronger")
							.executes(BlackHoleCommands::strongerDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageStrongest =
					CommandManager.literal("strongest")
							.executes(BlackHoleCommands::strongestDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleDamageOn =
					CommandManager.literal("on")
							.executes(BlackHoleCommands::onDamageStrength)
							.requires((source) -> source.hasPermissionLevel(2))
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHolePause =
					CommandManager.literal("pause")
							.executes(BlackHoleCommands::onPause)
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleResume =
					CommandManager.literal("resume")
							.executes(BlackHoleCommands::onResume)
							.build();
			
			LiteralCommandNode<ServerCommandSource> blackHoleLimit =
					CommandManager.literal("limit").then(
							CommandManager.argument("limit", IntegerArgumentType.integer())
									.executes(BlackHoleCommands::onLimit)
									.build()
					).build();
			
			
			blackHoleRoot.addChild(blackHoleSpawn);
			
			blackHoleRoot.addChild(blackHoleGrow);
			
			blackHoleGrow.addChild(blackHoleGrowOff);
			blackHoleGrow.addChild(blackHoleGrowSlowest);
			blackHoleGrow.addChild(blackHoleGrowSlower);
			blackHoleGrow.addChild(blackHoleGrowSlow);
			blackHoleGrow.addChild(blackHoleGrowStandard);
			blackHoleGrow.addChild(blackHoleGrowFast);
			blackHoleGrow.addChild(blackHoleGrowFaster);
			blackHoleGrow.addChild(blackHoleGrowFastest);
			blackHoleGrow.addChild(blackHoleGrowOn);
			
			blackHoleRoot.addChild(blackHoleFollow);
			
			blackHoleFollow.addChild(blackHoleFollowOff);
			blackHoleFollow.addChild(blackHoleFollowSlowest);
			blackHoleFollow.addChild(blackHoleFollowSlower);
			blackHoleFollow.addChild(blackHoleFollowSlow);
			blackHoleFollow.addChild(blackHoleFollowStandard);
			blackHoleFollow.addChild(blackHoleFollowFast);
			blackHoleFollow.addChild(blackHoleFollowFaster);
			blackHoleFollow.addChild(blackHoleFollowFastest);
			blackHoleFollow.addChild(blackHoleFollowOn);
			
			blackHoleRoot.addChild(blackHolePull);
			
			blackHolePull.addChild(blackHolePullOff);
			blackHolePull.addChild(blackHolePullSlowest);
			blackHolePull.addChild(blackHolePullSlower);
			blackHolePull.addChild(blackHolePullSlow);
			blackHolePull.addChild(blackHolePullStandard);
			blackHolePull.addChild(blackHolePullFast);
			blackHolePull.addChild(blackHolePullFaster);
			blackHolePull.addChild(blackHolePullFastest);
			blackHolePull.addChild(blackHolePullOn);
			
			blackHoleRoot.addChild(blackHoleDamage);
			
			blackHoleDamage.addChild(blackHoleDamageOff);
			blackHoleDamage.addChild(blackHoleDamageWeakest);
			blackHoleDamage.addChild(blackHoleDamageWeaker);
			blackHoleDamage.addChild(blackHoleDamageWeak);
			blackHoleDamage.addChild(blackHoleDamageStandard);
			blackHoleDamage.addChild(blackHoleDamageStrong);
			blackHoleDamage.addChild(blackHoleDamageStronger);
			blackHoleDamage.addChild(blackHoleDamageStrongest);
			blackHoleDamage.addChild(blackHoleDamageOn);
			
			blackHoleRoot.addChild(blackHolePause);
			
			blackHoleRoot.addChild(blackHoleResume);
			
			blackHoleRoot.addChild(blackHoleLimit);
			
			dispatcher.getRoot().addChild(blackHoleRoot);
		});
	}
}
