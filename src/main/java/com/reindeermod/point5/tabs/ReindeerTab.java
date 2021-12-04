package com.reindeermod.point5.tabs;

import com.reindeermod.point5.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReindeerTab extends CreativeTabs {

	public ReindeerTab(String label) {
        super("itemsblockstabcreatures");
    }

    @Override
    public ItemStack getTabIconItem() {

        return new ItemStack(Item.getItemFromBlock(Blocks.BRICK_BLOCK));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> itemList) {
        super.displayAllRelevantItems(itemList);
        for (EntityList.EntityEggInfo eggInfo : EntityList.ENTITY_EGGS.values()) {
            if (eggInfo.spawnedID.getResourceDomain().equals(Reference.ModID)) {
                ItemStack itemstack = new ItemStack(Items.SPAWN_EGG, 1);
                ItemMonsterPlacer.applyEntityIdToItemStack(itemstack, eggInfo.spawnedID);
                itemList.add(itemstack);
            }
        }
    }
	
}
