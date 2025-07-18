package com.mccisland.enhanced.config;

import com.mccisland.enhanced.MCCIslandEnhancedClient;

public class ModConfig {
    
    // General settings
    public boolean modEnabled = true;
    
    public ModConfig() {
        // Initialize with default values
    }
    
    public void load() {
        // Simple load method - in a real implementation this would load from file
        MCCIslandEnhancedClient.LOGGER.info("Configuration loaded");
    }
    
    public void save() {
        // Simple save method - in a real implementation this would save to file
        MCCIslandEnhancedClient.LOGGER.info("Configuration saved");
    }
}