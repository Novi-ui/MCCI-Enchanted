package net.minecraft.client.network;

import net.minecraft.util.math.Vec3d;

/**
 * Stub class for ClientPlayerEntity
 */
public class ClientPlayerEntity {
    
    public Vec3d getPos() {
        return new Vec3d(0, 0, 0);
    }
    
    public double getX() {
        return 0;
    }
    
    public double getY() {
        return 0;
    }
    
    public double getZ() {
        return 0;
    }
    
    public String getName() {
        return "Player";
    }
    
    public void tick() {
        // Stub implementation
    }
    
    public Object getBoundingBox() {
        return new Object();
    }
}