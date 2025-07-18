package net.minecraft.client.font;

import net.minecraft.text.Text;

/**
 * Stub class for TextRenderer
 */
public class TextRenderer {
    
    public int getWidth(String text) {
        return text.length() * 8; // Approximate width
    }
    
    public int getWidth(Text text) {
        return getWidth(text.getString());
    }
    
    public int fontHeight() {
        return 9;
    }
    
    public void draw(Object drawContext, String text, int x, int y, int color) {
        // Stub implementation
    }
    
    public void draw(Object drawContext, Text text, int x, int y, int color) {
        draw(drawContext, text.getString(), x, y, color);
    }
}