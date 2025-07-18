package com.mccisland.enhanced.features.universal;

import com.mccisland.enhanced.MCCIslandEnhancedClient;

/**
 * Optimizes particle rendering for better performance
 */
public class ParticleOptimizer {
    
    private boolean enabled = true;
    
    public ParticleOptimizer() {
        MCCIslandEnhancedClient.LOGGER.info("ParticleOptimizer initialized");
    }
    
    public void update() {
        if (!enabled) return;
        
        try {
            // Particle optimization logic
            MCCIslandEnhancedClient.LOGGER.debug("ParticleOptimizer update called");
        } catch (Exception e) {
            MCCIslandEnhancedClient.LOGGER.error("Error in ParticleOptimizer", e);
        }
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}