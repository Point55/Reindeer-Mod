package com.reindeermod.point5.entity.reindeer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class RendererReindeer extends GeoEntityRenderer<EntityReindeer>
{

	public RendererReindeer(RenderManager renderManager)
	{
		super(renderManager, new ModelReindeer());
		this.shadowSize = 1.0F;
	}
	
	@Override
	public void renderEarly(EntityReindeer animatable, float ticks, float red, float green, float blue, float partialTicks)
	{
		if (animatable.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
	        GlStateManager.scale(1.2F, 1.2F, 1.2F);
	}
}
