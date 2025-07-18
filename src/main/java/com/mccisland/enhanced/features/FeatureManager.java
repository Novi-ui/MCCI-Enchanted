package com.mccisland.enhanced.features;

import com.mccisland.enhanced.config.ModConfig;
import com.mccisland.enhanced.features.universal.ParticleOptimizer;
import com.mccisland.enhanced.MCCIslandEnhancedClient;

public class FeatureManager {
    private final ParticleOptimizer particleOptimizer;
    
    public FeatureManager() {
        MCCIslandEnhancedClient.LOGGER.info("Initializing FeatureManager");
        
        // Initialize features
        this.particleOptimizer = new ParticleOptimizer();
        
        MCCIslandEnhancedClient.LOGGER.info("FeatureManager initialized successfully");
    }
    
    public ParticleOptimizer getParticleOptimizer() {
        return particleOptimizer;
    }
    
    public void update() {
        // Update all features
        try {
            if (particleOptimizer != null) {
                particleOptimizer.update();
            }
        } catch (Exception e) {
            MCCIslandEnhancedClient.LOGGER.error("Error updating features", e);
        }
    }
}