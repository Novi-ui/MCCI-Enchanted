package net.minecraft.client.option;

/**
 * Stub class for KeyBinding
 */
public class KeyBinding {
    private final String translationKey;
    private final int defaultKey;
    private final String category;
    
    public KeyBinding(String translationKey, int defaultKey, String category) {
        this.translationKey = translationKey;
        this.defaultKey = defaultKey;
        this.category = category;
    }
    
    public boolean wasPressed() {
        return false;
    }
    
    public boolean isPressed() {
        return false;
    }
    
    public String getTranslationKey() {
        return translationKey;
    }
    
    public int getDefaultKey() {
        return defaultKey;
    }
    
    public String getCategory() {
        return category;
    }
}