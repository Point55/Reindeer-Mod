package com.reindeermod.point5.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class CommonProxy {

	 public void registerItemRenderer(Item item, int meta, String id)
	    {
	        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	    }

	    public void init(FMLInitializationEvent event) {
	        //EntityRegistry.addSpawn(EntityReindeer.class, 50, 1, 2, EnumCreatureType.CREATURE, Biomes.RIVER);
	       
	    }
	
}
