package com.reindeermod.point5;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import com.reindeermod.point5.proxy.CommonProxy;
import com.reindeermod.point5.util.Reference;
import com.reindeermod.point5.util.handlers.RegistryHandler;
import software.bernie.geckolib3.GeckoLib;
import com.reindeermod.point5.tabs.ReindeerTab;

@Mod(modid = Reference.ModID, version = Reference.VERSION, useMetadata=true, name = Reference.MOD_NAME)

public class ReindeerMod
{
    @Mod.Instance
    public static ReindeerMod instance;

    public static final CreativeTabs itemsblockstab = new ReindeerTab("itemsblockstabreindeer");

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        RegistryHandler.preInitRegistries(event);
        GeckoLib.initialize();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        RegistryHandler.postInitRegistries();
    }

    @Mod.EventHandler
    public static void serverStarting(FMLServerStartingEvent event) {
        RegistryHandler.serverRegistries();
    }
}
