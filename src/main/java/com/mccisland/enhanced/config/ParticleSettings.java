package com.mccisland.enhanced.config;

public class ParticleSettings {
    public boolean enabled = true;
    public ParticleDensity density = ParticleDensity.LOW;
    public float renderRadius = 10.0f;
    public boolean cullOutOfView = true;
    public boolean reduceStormParticles = true;
    public float stormParticleMultiplier = 0.1f;
    public boolean smartCulling = true;
    public int maxParticlesPerType = 500;
    
    public enum ParticleDensity {
        OFF("Off", 0.0f),
        LOW("Low", 0.3f),
        MEDIUM("Medium", 0.6f),
        HIGH("High", 1.0f);
        
        private final String displayName;
        private final float multiplier;
        
        ParticleDensity(String displayName, float multiplier) {
            this.displayName = displayName;
            this.multiplier = multiplier;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public float getMultiplier() {
            return multiplier;
        }
    }
}