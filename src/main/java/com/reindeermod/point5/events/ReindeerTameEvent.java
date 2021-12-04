package com.reindeermod.point5.events;


import com.reindeermod.point5.entity.reindeer.EntityReindeer;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

// TODO: Auto-generated Javadoc
/**
 * This event is fired when an {@link EntityAnimal} is tamed. <br>
 * It is fired via {@link ForgeEventFactory#onAnimalTame(EntityAnimal, EntityPlayer)}.
 * Forge fires this event for applicable vanilla animals, mods need to fire it themselves.
 * This event is {@link Cancelable}. If canceled, taming the animal will fail.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 */
@Cancelable
public class ReindeerTameEvent extends LivingEvent
{
    private final EntityReindeer theReindeer;
    private final EntityPlayer tamer;


    public ReindeerTameEvent(EntityReindeer parReindeer, EntityPlayer parTamer)
    {
        super(parReindeer);
        theReindeer = parReindeer;
        tamer = parTamer;
    }


    public EntityReindeer getAnimal()
    {
        return theReindeer;
    }


    public EntityPlayer getTamer()
    {
        return tamer;
    }
}