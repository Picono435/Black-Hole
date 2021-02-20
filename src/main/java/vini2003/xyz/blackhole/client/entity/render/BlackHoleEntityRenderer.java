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
		

		

		
		matrices.pop();
		

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
