package com.mccisland.enhanced.features;

import net.minecraft.client.gui.DrawContext;

public interface Feature {
    /**
     * Called every client tick when the feature is enabled
     */
    void tick();
    
    /**
     * Called when rendering the HUD
     */
    default void renderHud(DrawContext context, float tickDelta) {}
    
    /**
     * Called when rendering the world
     */
    default void renderWorld(DrawContext context, float tickDelta) {}
    
    /**
     * Check if this feature is currently enabled
     */
    boolean isEnabled();
    
    /**
     * Enable or disable this feature
     */
    void setEnabled(boolean enabled);
    
    /**
     * Get the display name of this feature
     */
    String getName();
    
    /**
     * Get the description of this feature
     */
    default String getDescription() {
        return "";
    }
}