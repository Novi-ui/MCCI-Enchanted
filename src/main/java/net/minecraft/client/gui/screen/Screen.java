package net.minecraft.client.gui.screen;

import net.minecraft.text.Text;

/**
 * Stub class for Screen
 */
public class Screen {
    protected Text title;
    
    public Screen(Text title) {
        this.title = title;
    }
    
    public void init() {
        // Stub implementation
    }
    
    public void render(Object drawContext, int mouseX, int mouseY, float delta) {
        // Stub implementation
    }
    
    public void close() {
        // Stub implementation
    }
    
    public Text getTitle() {
        return title;
    }
}