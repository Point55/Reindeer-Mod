package com.reindeermod.point5.objects.items;

import com.reindeermod.point5.ReindeerMod;
import com.reindeermod.point5.init.ItemInit;

import net.minecraft.item.Item;

public class ReindeerModItem extends Item {
    public ReindeerModItem(String name) {

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ReindeerMod.itemsblockstab);


        ItemInit.ITEMS.add(this);
    }

    public void registerModels() {

        ReindeerMod.proxy.registerItemRenderer(this, 0, "inventory");

    }

}
