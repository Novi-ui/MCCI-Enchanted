package net.minecraft.client.render;

/**
 * Stub class for GameRenderer
 */
public class GameRenderer {
    
    public void render(float tickDelta, long startTime, boolean tick) {
        // Stub implementation
    }
    
    public Object getCamera() {
        return new Object();
    }
    
    public float getFov(Object camera, float tickDelta, boolean changingFov) {
        return 70.0f;
    }
}