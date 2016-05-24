package com.techreimagined.common.config;

import com.techreimagined.TechReimagined;
import com.techreimagined.ModInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.io.File;
import java.util.Arrays;

public class Config extends GuiConfig {
    public static Configuration configuration;

    public Config(GuiScreen parentScreen) {
        super(
                parentScreen,
                Arrays.asList(new IConfigElement[]{
                        new ConfigElement(TechReimagined.configuration.getCategory("SampleData"))
                }),
                ModInfo.MOD_ID, false, false, "Tech Reimagined Configuration");
        titleLine2 = TechReimagined.configuration.getConfigFile().getAbsolutePath();
    }

    public static Configuration initConfig(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
        return configuration;
    }

    public static void loadConfiguration() {
        ConfigSample.init(configuration);

        configuration.save();
    }
}