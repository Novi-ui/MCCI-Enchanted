package net.minecraft.client.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

/**
 * Stub class for DrawContext
 */
public class DrawContext {
    
    public void drawText(TextRenderer textRenderer, String text, int x, int y, int color) {
        // Stub implementation
    }
    
    public void drawText(TextRenderer textRenderer, Text text, int x, int y, int color, boolean shadow) {
        // Stub implementation
    }
    
    public void drawTextWithShadow(TextRenderer textRenderer, String text, int x, int y, int color) {
        // Stub implementation
    }
    
    public void fill(int x1, int y1, int x2, int y2, int color) {
        // Stub implementation
    }
    
    public void drawBorder(int x, int y, int width, int height, int color) {
        // Stub implementation
    }
    
    public int getScaledWindowWidth() {
        return 1920;
    }
    
    public int getScaledWindowHeight() {
        return 1080;
    }
    
    public Object getMatrices() {
        return new Object();
    }
}