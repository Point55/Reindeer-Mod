package com.reindeermod.point5.proxy;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerProxy extends CommonProxy {
    public void onInit()
    {
        //GameRegistry.registerWorldGenerator(new CreaturesWorldGen(), 0);
    }
}
