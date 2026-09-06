package com.example.soullantern.init;

import com.example.soullantern.SoulLanternMod;
import com.example.soullantern.blocks.BlockSoulLantern;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SoulLanternMod.MOD_ID)
public class ModBlocks {
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
}
