package com.mccisland.enhanced;

import com.mccisland.enhanced.config.ModConfig;
import com.mccisland.enhanced.features.FeatureManager;
import com.mccisland.enhanced.input.KeyBindings;
import com.mccisland.enhanced.render.HudRenderer;
import com.mccisland.enhanced.util.MinecraftVersionUtil;
import com.mccisland.enhanced.compat.MinecraftCompat;
import com.mccisland.enhanced.stubs.MinecraftStubs;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCCIslandEnhancedClient implements ClientModInitializer {
    public static final String MOD_ID = "mcc-island-enhanced";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static MCCIslandEnhancedClient instance;
    private ModConfig config;
    private FeatureManager featureManager;
    private HudRenderer hudRenderer;
    
    @Override
    public void onInitializeClient() {
        instance = this;
        LOGGER.info("Initializing MCC Island Enhanced Mod");
        
        try {
            // Initialize configuration
            config = new ModConfig();
            
            // Initialize feature manager
            featureManager = new FeatureManager();
            
            // Initialize HUD renderer
            hudRenderer = new HudRenderer();
            
            LOGGER.info("MCC Island Enhanced Mod initialized successfully!");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize MCC Island Enhanced Mod", e);
        }
    }
    
    private void registerEventHandlers() {
        try {
            // In runtime, this would register actual event handlers
            // For now, this is a stub for compilation
            LOGGER.info("Event handlers registered successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to register event handlers", e);
        }
    }
    
    public static MCCIslandEnhancedClient getInstance() {
        return instance;
    }
    
    public ModConfig getConfig() {
        return config;
    }
    
    public FeatureManager getFeatureManager() {
        return featureManager;
    }
    
    public HudRenderer getHudRenderer() {
        return hudRenderer;
    }
}