package vini2003.xyz.blackhole.client.entity.render;

import dev.monarkhes.myron.api.Myron;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import vini2003.xyz.blackhole.BlackHole;
import vini2003.xyz.blackhole.BlackHoleClient;
import vini2003.xyz.blackhole.client.utilities.BlackHoleClientUtilities;
import vini2003.xyz.blackhole.common.entity.BlackHoleEntity;

import java.util.Iterator;

public class BlackHoleEntityRenderer extends EntityRenderer<BlackHoleEntity> {
	public static final Identifier MODEL_LOCATION = BlackHole.identifier("models/misc/black_sphere");
	
	private BakedModel model;
	
	public BlackHoleEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}
	
	@Override
	public void render(BlackHoleEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		if (model == null) {
			model = Myron.getModel(MODEL_LOCATION);
		}
		
		matrices.push();
		
		double interpolatedEntityAge = MathHelper.lerp(tickDelta, entity.age - 1, entity.age);
		
		float size = entity.getSize();
		
		// Render the sphere.
		if (model != null) {
			matrices.translate(0, Math.sin(interpolatedEntityAge / 20F) * 0.25F, 0);
			
			matrices.scale(size, size, size);
			
			MatrixStack.Entry matricesEntry = matrices.peek();
			
			VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
			
			model.getQuads(null, null, entity.world.getRandom()).forEach(quad -> {
				consumer.quad(matricesEntry, quad, 0.0F, 0.0F, 0.0F, light, OverlayTexture.DEFAULT_UV);
			});
		}
		
		// Black out players inside the black hole.
		if (MinecraftClient.getInstance().player != null && entity.getPos().subtract(0, 1.75, 0).distanceTo(MinecraftClient.getInstance().player.getPos()) <= size) {
			BlackHoleClient.isBlackedOut = true;
		}
		
		matrices.pop();
		
		ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
		
		PlayerEntity player = BlackHoleClientUtilities.getPlayer();
		
		// Move particles towards black hole.
		for (Iterator<BlackHoleEntity.Particle> particleIterator = entity.getParticles().iterator(); particleIterator.hasNext(); ) {
			BlackHoleEntity.Particle particle = particleIterator.next();
			
			matrices.push();
			
			double distanceToBlackHole = particle.getPos().distanceTo(entity.getPos());
			
			double distanceToPlayer = particle.getPos().distanceTo(player.getPos());
			
			if (distanceToBlackHole < entity.getSize() || distanceToPlayer > 128) {
				particleIterator.remove();
				
				matrices.pop();
				
				continue;
			}
			
			Vec3d pull = particle.getPos().subtract(entity.getPos()).normalize().multiply(tickDelta * 0.125F + Math.min(8, (distanceToBlackHole - size)) * 0.025F);
			
			particle.setPos(particle.getPos().subtract(pull));
			
			matrices.translate(particle.getPos().getX() - entity.getX(), particle.getPos().getY() - entity.getY(), particle.getPos().getZ() - entity.getZ());
			
			itemRenderer.renderItem(particle.getStack(), ModelTransformation.Mode.NONE, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
			
			matrices.pop();
		}
	}
	
	@Override
	public boolean shouldRender(BlackHoleEntity entity, Frustum frustum, double x, double y, double z) {
		return true;
	}
	
	@Override
	public Identifier getTexture(BlackHoleEntity entity) {
		return null;
	}
}
