package net.minecraft.entity.player;

import net.minecraft.util.math.Vec3d;

/**
 * Stub class for PlayerEntity
 */
public class PlayerEntity {
    
    public Vec3d getPos() {
        return new Vec3d(0, 0, 0);
    }
    
    public String getName() {
        return "Player";
    }
    
    public boolean isSpectator() {
        return false;
    }
    
    public boolean isCreative() {
        return false;
    }
    
    public Object getGameProfile() {
        return new Object();
    }
    
    public float getHealth() {
        return 20.0f;
    }
    
    public float getMaxHealth() {
        return 20.0f;
    }
    
    public float getHeight() {
        return 1.8f;
    }
}