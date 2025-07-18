package net.minecraft.client.render;

import net.minecraft.util.math.Vec3d;

/**
 * Stub class for Camera
 */
public class Camera {
    
    public Vec3d getPos() {
        return new Vec3d(0, 0, 0);
    }
    
    public float getYaw() {
        return 0.0f;
    }
    
    public float getPitch() {
        return 0.0f;
    }
    
    public boolean isReady() {
        return true;
    }
}