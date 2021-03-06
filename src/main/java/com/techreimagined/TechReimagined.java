package com.techreimagined;

import com.techreimagined.api.registry.WrenchRegistry;
import com.techreimagined.common.config.Config;
import com.techreimagined.common.integrations.IntegrationsManager;
import com.techreimagined.common.util.LogHelper;
import com.techreimagined.common.util.power.TeslaHelper;
import com.techreimagined.proxy.IProxy;
import com.google.common.base.Stopwatch;
import me.modmuss50.jsonDestroyer.JsonDestroyer;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, certificateFingerprint = ModInfo.FINGERPRINT, dependencies = ModInfo.DEPENDENCIES, version = ModInfo.VERSION_BUILD, guiFactory = ModInfo.GUI_FACTORY, acceptedMinecraftVersions = "[1.9.4]")
public class TechReimagined {
    @Mod.Instance(ModInfo.MOD_ID)
    public static TechReimagined instance;
    public static JsonDestroyer jsonDestroyer = new JsonDestroyer();

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    public static Configuration configuration;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        LogHelper.info("Pre Initialization (Started)");

        proxy.registerConfiguration(event.getSuggestedConfigurationFile());

        proxy.registerBlocks();
        proxy.registerItems();
        proxy.registerOreDict();

        proxy.registerGUIs();

        proxy.registerFurnaceRecipes();
        proxy.registerEvents();

        proxy.registerRenderers();

        IntegrationsManager.instance().index();
        IntegrationsManager.instance().preInit();

        LogHelper.info("Pre Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        LogHelper.info("Initialization (Started)");
        jsonDestroyer.load();
        CableManager.init();

        TeslaHelper.load();
        proxy.registerRecipes();

        IntegrationsManager.instance().init();

        LogHelper.info("Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        LogHelper.info("Post Initialization (Started)");
        CableManager.postInit();

        IntegrationsManager.instance().postInit();

        LogHelper.info("Post Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ModInfo.MOD_ID)) {
            Config.loadConfiguration();
        }
    }
}
