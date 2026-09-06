package com.example.soullantern;

import com.example.soullantern.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SoulLanternMod.MOD_ID, name = SoulLanternMod.NAME, version = SoulLanternMod.VERSION)
public class SoulLanternMod {
    public static final String MOD_ID = "soullantern";
    public static final String NAME = "Soul Lantern Mod";
    public static final String VERSION = "1.0.0";

    @Mod.Instance
    public static SoulLanternMod instance;

    @SidedProxy(
        clientSide = "com.example.soullantern.proxy.ClientProxy",
        serverSide = "com.example.soullantern.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // ✅ 移除 ModBlocks.init() - 不需要，因为使用了 @EventBusSubscriber
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }
}
