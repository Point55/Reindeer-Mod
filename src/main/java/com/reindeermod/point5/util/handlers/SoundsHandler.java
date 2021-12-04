package com.reindeermod.point5.util.handlers;

import com.reindeermod.point5.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Reference.ModID)
public class SoundsHandler 
{	
	public static SoundEvent REINDEER_HURT;
	public static SoundEvent REINDEER_DEATH;

	
	
	
	public static void registerSounds()
	{
		REINDEER_HURT = registerSound("entity.reindeer.reindeer_hurt");
		REINDEER_DEATH = registerSound("entity.reindeer.reindeer_death");
	}
	
	private static SoundEvent registerSound(String name)
	{
		ResourceLocation location = new ResourceLocation(Reference.ModID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}

}
