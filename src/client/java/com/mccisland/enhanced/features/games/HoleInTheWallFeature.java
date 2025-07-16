package com.mccisland.enhanced.features.games;

import com.mccisland.enhanced.config.HoleInTheWallSettings;
import com.mccisland.enhanced.features.Feature;

public class HoleInTheWallFeature implements Feature {
    private final HoleInTheWallSettings settings;
    
    public HoleInTheWallFeature(HoleInTheWallSettings settings) {
        this.settings = settings;
    }
    
    @Override
    public void tick() {
        // TODO: Implement Hole in the Wall specific logic
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
        return "Hole in the Wall";
    }
    
    @Override
    public String getDescription() {
        return "Hole in the Wall pattern preview and positioning assistance";
    }
}