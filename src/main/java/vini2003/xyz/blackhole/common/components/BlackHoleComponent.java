package vini2003.xyz.blackhole.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vini2003.xyz.blackhole.common.config.BlackHoleConfig;
import vini2003.xyz.blackhole.common.utilities.BlackHoleUtilities;
import vini2003.xyz.blackhole.registry.common.BlackHoleComponents;

import java.util.ArrayList;
import java.util.List;

public class BlackHoleComponent implements Component {
	private Vec3d pos = new Vec3d(0D, 64D, 0D);
	
	private float size = 1F;
	
	private int countdownToDamage = 1200;
	
	private int id;
	
	private World world;
	
	private PlayerEntity target;
	
	private final List<BlackHoleParticle> particles = new ArrayList<>();
	
	public BlackHoleComponent(World world) {
		this.world = world;
		
		this.id = BlackHoleComponents.BLACK_HOLES.get(world).nextId();
	}
	
	public void tickSize() {
		if (BlackHoleConfig.cache.grow) {
			size = Math.min(BlackHoleConfig.cache.limit, size * (1 + BlackHoleConfig.cache.growSpeed));
		}
	}
	
	public void tickDestruction() {
		BlackHoleUtilities.destroy(this, world, (int) pos.getX(), (int)  pos.getY(), (int) pos.getZ(), (int) getSize());
	}
	
	public void tickPlayerPull() {
		for (Entity entity : world.getPlayers()) {
			if (!entity.isSpectator() && !((PlayerEntity) entity).isCreative()) {
				double distance = getPos().distanceTo(entity.getPos());
				
				if (world.isClient) {
					Vec3d pull = getPos().subtract(entity.getPos()).normalize();
					pull = pull.multiply(BlackHoleConfig.cache.pullSpeed / (distance - size));
					
					// If velocity is lower than 1 in each axis, proceed.
					if (distance > size && entity.getVelocity().lengthSquared() < 3) {
						entity.addVelocity(Math.min(1F - entity.getVelocity().getX(), pull.getX()), Math.min(1F - entity.getVelocity().getY(), pull.getY()), Math.min(1F - entity.getVelocity().getZ(), pull.getZ()));
						entity.velocityModified = true;
						entity.velocityDirty = true;
					}
					
					if (distance < size) {
						entity.setVelocity(Vec3d.ZERO);
					}
				} else {
					if (countdownToDamage <= 0 && distance < size) {
						entity.kill();
					}
				}
			}
		}
	}
	
	public void tickEntityPull() {
		if (!world.isClient) {
			for (Entity entity : ((ServerWorld) world).iterateEntities()) {
				double distance = getPos().distanceTo(entity.getPos());
				
				if (BlackHoleConfig.cache.pull) {
					if (!(entity instanceof PlayerEntity)) {
						Vec3d pull = getPos().subtract(entity.getPos()).normalize();
						pull = pull.multiply(BlackHoleConfig.cache.pullSpeed / (distance - size));
						
						// If velocity is lower than 1 in each axis, proceed.
						if (distance > size && entity.getVelocity().lengthSquared() < 3) {
							entity.addVelocity(Math.min(1F - entity.getVelocity().getX(), pull.getX()), Math.min(1F - entity.getVelocity().getY(), pull.getY()), Math.min(1F - entity.getVelocity().getZ(), pull.getZ()));
							entity.velocityModified = true;
							entity.velocityDirty = true;
						}
						
						if (countdownToDamage <= 0 && distance < size) {
							entity.kill();
							entity.setVelocity(Vec3d.ZERO);
						}
					}
				}
			}
		}
	}
	
	public void tickTarget() {
		if (BlackHoleConfig.cache.follow) {
			if (target == null || !target.isAlive() || target.isSpectator() || target.isCreative()) {
				target = world.getPlayers().stream().filter(player -> !player.isSpectator() && !player.isCreative() && player.squaredDistanceTo(getPos()) < 1024 * 1024).findFirst().orElse(null);
			} else {
				Vec3d subtract = target.getPos().subtract(getPos());
				Vec3d follow = subtract.normalize().multiply(BlackHoleConfig.cache.followSpeed);
				
				setPos(getPos().add(follow));
			}
		}
	}
	
	public void tickCountdown() {
		if (countdownToDamage > 0) {
			--countdownToDamage;
		}
	}
	
	public void tick() {
		if (!world.isClient) {
			tickSize();
		}
		
		tickDestruction();
		
		tickPlayerPull();
		
		if (!world.isClient) {
			tickEntityPull();
		}
		
		if (!world.isClient) {
			tickTarget();
		}
		
		if (!world.isClient) {
			tickCountdown();
		}
	}
	
	// Getters and Setters //
	public Vec3d getPos() {
		return pos;
	}
	
	public void setPos(Vec3d pos) {
		this.pos = pos;
	}
	
	public float getSize() {
		return size;
	}
	
	public void setSize(float size) {
		this.size = size;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public List<BlackHoleParticle> getParticles() {
		return particles;
	}
	
	// Particle //
	public static class BlackHoleParticle {
		private Vec3d pos;
		
		private final BlockState state;
		
		private final ItemStack stack;
		
		public BlackHoleParticle(Vec3d pos, BlockState state) {
			this.pos = pos;
			this.state = state;
			
			this.stack = new ItemStack(state.getBlock().asItem());
		}
		
		public Vec3d getPos() {
			return pos;
		}
		
		public void setPos(Vec3d pos) {
			this.pos = pos;
		}
		
		public BlockState getState() {
			return state;
		}
		
		public ItemStack getStack() {
			return stack;
		}
	}
	
	// Serialization //
	@Override
	public void readFromNbt(CompoundTag compoundTag) {
		pos = new Vec3d(
				compoundTag.getDouble("X"),
				compoundTag.getDouble("Y"),
				compoundTag.getDouble("Z")
		);
		
		size = compoundTag.getFloat("Size");
		
		countdownToDamage = compoundTag.getInt("CountdownToDamage");
		
		id = compoundTag.getInt("Id");
	}
	
	@Override
	public void writeToNbt(CompoundTag compoundTag) {
		compoundTag.putDouble("X", pos.getX());
		compoundTag.putDouble("Y", pos.getY());
		compoundTag.putDouble("Z", pos.getZ());
		
		compoundTag.putFloat("Size", size);
		
		compoundTag.putInt("CountdownToDamage", countdownToDamage);
		
		compoundTag.putInt("Id", id);
	}
}
