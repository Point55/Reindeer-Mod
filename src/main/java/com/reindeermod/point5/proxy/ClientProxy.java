package com.reindeermod.point5.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	 @Override
	    public void registerItemRenderer(Item item, int meta, String id)
	    {
	        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

	    }

	    @SubscribeEvent
	    public void registerRenders(final ModelRegistryEvent event) {
	    }
	
}
