package com.mccisland.enhanced.features.games;

import com.mccisland.enhanced.config.AceRaceSettings;
import com.mccisland.enhanced.features.Feature;

public class AceRaceFeature implements Feature {
    private final AceRaceSettings settings;
    
    public AceRaceFeature(AceRaceSettings settings) {
        this.settings = settings;
    }
    
    @Override
    public void tick() {
        // TODO: Implement Ace Race specific logic
    }
    
    @Override
    public boolean isEnabled() {
        return settings.enabled;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        settings.enabled = enabled;
    }
    
    @Override
    public String getName() {
        return "Ace Race";
    }
    
    @Override
    public String getDescription() {
        return "Ace Race lap timing and racing line optimization";
    }
}