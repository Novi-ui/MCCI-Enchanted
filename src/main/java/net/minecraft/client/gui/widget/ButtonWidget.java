package net.minecraft.client.gui.widget;

import net.minecraft.text.Text;

/**
 * Stub class for ButtonWidget
 */
public class ButtonWidget {
    
    public ButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        // Stub implementation
    }
    
    public void render(Object drawContext, int mouseX, int mouseY, float delta) {
        // Stub implementation
    }
    
    @FunctionalInterface
    public interface PressAction {
        void onPress(ButtonWidget button);
    }
}