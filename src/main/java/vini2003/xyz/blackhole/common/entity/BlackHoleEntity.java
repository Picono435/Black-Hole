package vini2003.xyz.blackhole.common.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vini2003.xyz.blackhole.BlackHole;
import vini2003.xyz.blackhole.common.config.BlackHoleConfig;
import vini2003.xyz.blackhole.common.utilities.BlackHoleUtilities;
import vini2003.xyz.blackhole.registry.common.BlackHoleDamageSources;
import vini2003.xyz.blackhole.registry.common.BlackHoleEntityTypes;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class BlackHoleEntity extends Entity {
	public static final Identifier SPAWN_PACKET = BlackHole.identifier("spawn");
	
	public static final TrackedData<Float> SIZE = DataTracker.registerData(BlackHoleEntity.class, TrackedDataHandlerRegistry.FLOAT);
	
	private final List<Particle> particles = new ArrayList<>();
	
	public PlayerEntity target = null;
	
	public BlackHoleEntity(EntityType<?> type, World world) {
		super(type, world);
		this.ignoreCameraFrustum = true;
		this.noClip = true;
		this.setNoGravity(true);
	}
	
	public void updateSize() {
		setSize(Math.min(BlackHoleConfig.cache.limit, getSize()));
		
		if (BlackHoleConfig.cache.grow) {
			setSize(Math.min(BlackHoleConfig.cache.limit, (float) Math.max(1, getSize() * (1 + (BlackHoleConfig.cache.growthSpeed)))));
		} else {
			if (getSize() == 0F) {
				setSize(1F);
			}
		}
	}
	
	public void updateDestruction() {
		BlackHoleUtilities.destroy(this, world, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), (int) getSize());
	}
	
	@Override
	public void tick() {
		super.tick();
		
		float size = getSize();
		
		if (!world.isClient) {
			updateSize();
		}
		
		updateDestruction();
		
		// Move players.
		if (world.isClient) {
			for (Entity entity : world.getPlayers()) {
				if (!entity.isSpectator() && !((PlayerEntity) entity).isCreative()) {
					double distance = getPos().distanceTo(entity.getPos());
					
					Vec3d pull = getPos().subtract(entity.getPos()).normalize();
					pull = pull.multiply(BlackHoleConfig.cache.pullSpeed / (distance - size) * (BlackHoleConfig.cache.pullSpeed));
					
					// If velocity is lower than 1 in each axis, proceed.
					if (entity.getVelocity().lengthSquared() < 3) {
						entity.addVelocity(pull.getX(), pull.getY(), pull.getZ());
						entity.velocityModified = true;
						entity.velocityDirty = true;
					}
				}
			}
		}
		
		// Move entities; damage them and players.
		if (!world.isClient) {
			for (Entity entity : ((ServerWorld) world).iterateEntities()) {
				if (entity.getType() == BlackHoleEntityTypes.BLACK_HOLE) continue;
				
				double distance = getPos().distanceTo(entity.getPos());
				
				if (BlackHoleConfig.cache.damage) {
					double damage = (BlackHoleConfig.cache.damageStrength / sqrt(distance));
					
					// If is not living and is within size, kill.
					if (distance < size) {
						if (!(entity instanceof LivingEntity)) {
							entity.kill();
							
							continue;
						}
						
						damage = Integer.MAX_VALUE;
					}
					
					// Otherwise, damage.
					if (damage >= 1D) {
						entity.damage(BlackHoleDamageSources.BLACK_HOLE, (float) damage);
					}
				}
				
				if (BlackHoleConfig.cache.pull) {
					if (!(entity instanceof PlayerEntity)) {
						Vec3d pull = getPos().subtract(entity.getPos()).normalize();
						pull = pull.multiply(BlackHoleConfig.cache.pullSpeed / (distance - size) * (BlackHoleConfig.cache.pullSpeed));
						
						// If velocity is lower than 1 in each axis, proceed.
						if (entity.getVelocity().lengthSquared() < 3) {
							entity.addVelocity(pull.getX(), pull.getY(), pull.getZ());
							entity.velocityModified = true;
							entity.velocityDirty = true;
						}
					}
				}
			}
		}
		
		// Follow players.
		if (BlackHoleConfig.cache.follow) {
			if (target == null || !target.isAlive() || target.isSpectator() || target.isCreative()) {
				target = world.getPlayers().stream().filter(player -> !player.isSpectator() && !player.isCreative() && player.squaredDistanceTo(getPos()) < 1024 * 1024).findFirst().orElse(null);
			} else {
				Vec3d subtract = target.getPos().subtract(getPos());
				Vec3d normalize = subtract.normalize();
				
				this.move(MovementType.SELF, normalize.multiply(BlackHoleConfig.cache.followSpeed));
			}
		}
	}
	
	@Override
	public boolean shouldRender(double distance) {
		return true;
	}
	
	@Override
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return true;
	}
	
	@Override
	public void initDataTracker() {
		dataTracker.startTracking(SIZE, 0F);
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		setSize(tag.getFloat("Size"));
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		tag.putFloat("Size", getSize());
	}
	
	@Override
	public Packet<?> createSpawnPacket() {
		PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
		
		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());
		packet.writeInt(getEntityId());
		packet.writeUuid(getUuid());
		
		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
	}
	
	public float getSize() {
		return dataTracker.get(SIZE);
	}
	
	public void setSize(float size) {
		dataTracker.set(SIZE, size);
	}
	
	public Iterable<Particle> getParticles() {
		return particles;
	}
	
	public static final class Particle {
		private Vec3d pos;
		
		private final BlockState state;
		
		private final ItemStack stack;
		
		public Particle(Vec3d pos, BlockState state) {
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
	
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	
	public void removeParticle(Particle particle) {
		particles.remove(particle);
	}
}
