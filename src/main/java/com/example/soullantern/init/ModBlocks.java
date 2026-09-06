package com.example.soullantern.init;

import com.example.soullantern.SoulLanternMod;
import com.example.soullantern.blocks.BlockSoulLantern;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = SoulLanternMod.MOD_ID)
public class ModBlocks {
    // ✅ 添加 public static 修饰符
    public static final Block SOUL_LANTERN = new BlockSoulLantern();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(SOUL_LANTERN);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ItemBlock itemBlock = new ItemBlock(SOUL_LANTERN);
        itemBlock.setRegistryName(SOUL_LANTERN.getRegistryName());
        event.getRegistry().register(itemBlock);
    }

    // ✅ 添加模型注册事件
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        Item item = Item.getItemFromBlock(SOUL_LANTERN);
        ModelLoader.setCustomModelResourceLocation(
            item, 0,
            new ModelResourceLocation(item.getRegistryName(), "inventory")
        );
    }
}
