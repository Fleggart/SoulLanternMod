package com.example.soullantern.proxy;

import com.example.soullantern.init.ModBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        registerRenderers();
    }

    @Override
    public void registerRenderers() {
        // ✅ 直接获取 Item，不使用 ModBlocks.ITEMS
        Item item = Item.getItemFromBlock(ModBlocks.SOUL_LANTERN);
        ModelLoader.setCustomModelResourceLocation(
            item, 0,
            new ModelResourceLocation(item.getRegistryName(), "inventory")
        );
    }
}
