package net.minecraft.particle;

/**
 * Stub class for ParticleTypes
 */
public class ParticleTypes {
    public static final ParticleEffect FLAME = new ParticleEffect() {
        @Override
        public String getType() {
            return "flame";
        }
    };
    
    public static final ParticleEffect SMOKE = new ParticleEffect() {
        @Override
        public String getType() {
            return "smoke";
        }
    };
    
    public static final ParticleEffect EXPLOSION = new ParticleEffect() {
        @Override
        public String getType() {
            return "explosion";
        }
    };
}