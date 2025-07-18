package net.minecraft.client.particle;

import net.minecraft.util.math.Vec3d;

/**
 * Stub class for Particle
 */
public class Particle {
    
    public Vec3d getPos() {
        return new Vec3d(0, 0, 0);
    }
    
    public void tick() {
        // Stub implementation
    }
    
    public void render(Object vertexConsumer, Object camera, float tickDelta) {
        // Stub implementation
    }
    
    public boolean isAlive() {
        return true;
    }
}