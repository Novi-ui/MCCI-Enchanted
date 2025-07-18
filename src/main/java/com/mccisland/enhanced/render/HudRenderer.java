package com.mccisland.enhanced.render;

import com.mccisland.enhanced.MCCIslandEnhancedClient;

/**
 * Handles rendering of HUD elements for the mod
 */
public class HudRenderer {
    
    public HudRenderer() {
        MCCIslandEnhancedClient.LOGGER.info("HudRenderer initialized");
    }
    
    /**
     * Main render method called by the HUD render callback
     */
    public void render(Object drawContext, float tickDelta) {
        try {
            // Simple HUD rendering logic
            MCCIslandEnhancedClient.LOGGER.debug("HUD render called");
        } catch (Exception e) {
            MCCIslandEnhancedClient.LOGGER.error("Error rendering HUD", e);
        }
    }
}