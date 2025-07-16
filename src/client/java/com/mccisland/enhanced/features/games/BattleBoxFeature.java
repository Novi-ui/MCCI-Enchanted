package com.mccisland.enhanced.features.games;

import com.mccisland.enhanced.config.BattleBoxSettings;
import com.mccisland.enhanced.features.Feature;
import net.minecraft.client.gui.DrawContext;

public class BattleBoxFeature implements Feature {
    private final BattleBoxSettings settings;
    
    public BattleBoxFeature(BattleBoxSettings settings) {
        this.settings = settings;
    }
    
    @Override
    public void tick() {
        // TODO: Implement Battle Box specific logic
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
        return "Battle Box";
    }
    
    @Override
    public String getDescription() {
        return "Battle Box wool counter and tactical features";
    }
}