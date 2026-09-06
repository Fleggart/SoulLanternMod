package com.example.soullantern.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SoulLanternMod.MOD_ID)
public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    
    // 如果有额外物品，在这里添加
    
    public static void init() {
        // 物品初始化
    }
}
