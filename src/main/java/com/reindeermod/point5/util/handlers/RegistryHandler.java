package com.reindeermod.point5.util.handlers;

import com.reindeermod.point5.ReindeerMod;
import com.reindeermod.point5.init.BlockInit;
import com.reindeermod.point5.init.EntityInit;
import com.reindeermod.point5.init.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryHandler {

	@SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));

    }
    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent event) {
        for (Item item : ItemInit.ITEMS) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
        for (Block block : BlockInit.BLOCKS) {
            ReindeerMod.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");

        }
        RenderHandler.registerEntityRenders();
    }

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));

    }

    public static void preInitRegistries(FMLPreInitializationEvent event)
    {
        EntityInit.registerEntities();
        RenderHandler.registerEntityRenders();
        SoundsHandler.registerSounds();
    }
    public static void initRegistries()
    {

    }
    public static void postInitRegistries()
    {

    }
    public static void serverRegistries()
    {

    }

}
