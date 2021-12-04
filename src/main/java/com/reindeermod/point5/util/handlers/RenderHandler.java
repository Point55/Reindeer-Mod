package com.reindeermod.point5.util.handlers;

import com.reindeermod.point5.entity.reindeer.EntityReindeer;
import com.reindeermod.point5.entity.reindeer.RendererReindeer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

	 public static void registerEntityRenders()
	    {
	        RenderingRegistry.registerEntityRenderingHandler(EntityReindeer.class, RendererReindeer::new);
	    }
	
}
