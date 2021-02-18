package vini2003.xyz.blackhole.registry.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

public class BlackHoleRenderLayers extends RenderLayer {
	private BlackHoleRenderLayers(String name, VertexFormat vertexFormat, int drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
		super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
	}
	
	public static final RenderLayer BLACK_SPHERE = of("black_sphere", VertexFormats.POSITION_COLOR, 7, 256, MultiPhaseParameters.builder().build(false));
}
