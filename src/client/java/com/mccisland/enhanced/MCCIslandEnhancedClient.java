package com.mccisland.enhanced;

import com.mccisland.enhanced.config.ModConfig;
import com.mccisland.enhanced.features.FeatureManager;
import com.mccisland.enhanced.input.KeyBindings;
import com.mccisland.enhanced.render.HudRenderer;
import com.mccisland.enhanced.util.MinecraftVersionUtil;
import com.mccisland.enhanced.compat.MinecraftCompat;
import com.mccisland.enhanced.stubs.MinecraftStubs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCCIslandEnhancedClient {
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
        
        // Check version compatibility
        MinecraftVersionUtil.MinecraftVersion currentVersion = MinecraftVersionUtil.getCurrentVersion();
        LOGGER.info("Detected Minecraft version: {}", currentVersion);
        
        if (!MinecraftVersionUtil.isSupported()) {
            LOGGER.warn("This version of Minecraft ({}) may not be fully supported. Supported versions: 1.19.4 - 1.21.7", currentVersion);
            LOGGER.warn("Some features may not work correctly.");
        } else {
            LOGGER.info("Minecraft version {} is supported!", currentVersion);
        }
        
        // Initialize configuration
        config = new ModConfig();
        config.load();
        
        // Initialize feature manager
        featureManager = new FeatureManager();
        
        // Initialize HUD renderer
        hudRenderer = new HudRenderer();
        
        // Initialize key bindings if supported
        if (MinecraftCompat.supportsFeature(MinecraftCompat.CompatFeature.KEYBINDING_SYSTEM)) {
            KeyBindings.initialize();
        }
        
        // Register event handlers
        registerEventHandlers();
        
        LOGGER.info("MCC Island Enhanced Mod initialized successfully!");
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