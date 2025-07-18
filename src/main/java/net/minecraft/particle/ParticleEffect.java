package net.minecraft.particle;

/**
 * Stub class for ParticleEffect
 */
public interface ParticleEffect {
    
    String getType();
    
    default boolean shouldCull() {
        return false;
    }
}