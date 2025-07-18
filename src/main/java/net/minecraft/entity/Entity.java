package net.minecraft.entity;

import net.minecraft.util.math.Vec3d;

/**
 * Stub class for Entity
 */
public class Entity {
    
    public Vec3d getPos() {
        return new Vec3d(0, 0, 0);
    }
    
    public String getName() {
        return "Entity";
    }
    
    public boolean isAlive() {
        return true;
    }
}