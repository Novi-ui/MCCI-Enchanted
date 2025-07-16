package com.mccisland.enhanced;

import com.mccisland.enhanced.config.ModConfig;
import com.mccisland.enhanced.features.FeatureManager;
import com.mccisland.enhanced.input.KeyBindings;
import com.mccisland.enhanced.render.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
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
        
        // Initialize configuration
        config = new ModConfig();
        config.load();
        
        // Initialize key bindings
        KeyBindings.register();
        
        // Initialize feature manager
        featureManager = new FeatureManager(config);
        
        // Initialize HUD renderer
        hudRenderer = new HudRenderer(config, featureManager);
        
        // Register events
        registerEvents();
        
        LOGGER.info("MCC Island Enhanced Mod initialized successfully");
    }
    
    private void registerEvents() {
        // Register HUD rendering
        HudRenderCallback.EVENT.register(hudRenderer::render);
        
        // Register client tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            KeyBindings.handleInput();
            featureManager.tick();
        });
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