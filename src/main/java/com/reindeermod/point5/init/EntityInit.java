package com.reindeermod.point5.init;

import com.reindeermod.point5.ReindeerMod;
import com.reindeermod.point5.entity.reindeer.EntityReindeer;
import com.reindeermod.point5.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {
	public static String[] REINDEER = {"FOREST", "MOUNTAIN", "HILLS"};
	
	public static void registerEntities() 
	{
		registerEntity("reindeer", EntityReindeer.class, Reference.ENTITY_REINDEER, 128, 14460200, 13995816);
	}
 
	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) 
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.ModID + ":" + name), entity, name, id, ReindeerMod.instance, range, 1, true, color1, color2);
	}
}
