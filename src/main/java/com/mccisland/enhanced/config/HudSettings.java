package com.mccisland.enhanced.config;

public class HudSettings {
    public boolean enabled = true;
    public float scale = 1.0f;
    public int xOffset = 10;
    public int yOffset = 10;
    public boolean showBackground = true;
    public float backgroundOpacity = 0.5f;
    public boolean rainbowText = false;
    public boolean highContrast = false;
    public boolean largeText = false;
    public boolean soundAlerts = false;
    public HudPosition position = HudPosition.TOP_LEFT;
    
    public enum HudPosition {
        TOP_LEFT("Top Left"),
        TOP_RIGHT("Top Right"),
        BOTTOM_LEFT("Bottom Left"),
        BOTTOM_RIGHT("Bottom Right"),
        CENTER("Center");
        
        private final String displayName;
        
        HudPosition(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}