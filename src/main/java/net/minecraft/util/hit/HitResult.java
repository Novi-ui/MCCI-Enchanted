package net.minecraft.util.hit;

import net.minecraft.util.math.Vec3d;

/**
 * Stub class for HitResult
 */
public class HitResult {
    
    public Vec3d getPos() {
        return new Vec3d(0, 0, 0);
    }
    
    public Type getType() {
        return Type.MISS;
    }
    
    public enum Type {
        MISS,
        BLOCK,
        ENTITY
    }
}