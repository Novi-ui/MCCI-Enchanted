package com.mccisland.enhanced.features.universal;

import com.mccisland.enhanced.config.ParticleSettings;
import com.mccisland.enhanced.features.Feature;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;

public class ParticleOptimizer implements Feature {
    private final ParticleSettings settings;
    private final MinecraftClient client;
    private final Map<String, Integer> particleCounts;
    
    public ParticleOptimizer(ParticleSettings settings) {
        this.settings = settings;
        this.client = MinecraftClient.getInstance();
        this.particleCounts = new HashMap<>();
    }
    
    @Override
    public void tick() {
        if (!isEnabled()) return;
        
        // Reset particle counts each tick
        particleCounts.clear();
    }
    
    /**
     * Determines if a particle should be spawned based on optimization settings
     */
    public boolean shouldSpawnParticle(ParticleEffect particleType, Vec3d position) {
        if (!isEnabled()) return true;
        
        // Special handling for storm particles
        if (isStormParticle(particleType)) {
            return shouldSpawnStormParticle(position);
        }
        
        // General particle optimization
        return shouldSpawnGeneralParticle(particleType, position);
    }
    
    private boolean isStormParticle(ParticleEffect particleType) {
        // Check if this is a storm-related particle
        return particleType == ParticleTypes.RAIN ||
               particleType == ParticleTypes.CLOUD ||
               particleType == ParticleTypes.WHITE_ASH ||
               particleType == ParticleTypes.FALLING_WATER;
    }
    
    private boolean shouldSpawnStormParticle(Vec3d position) {
        if (!settings.reduceStormParticles) return true;
        
        // Only spawn storm particles within the render radius
        if (client.player != null) {
            double distance = client.player.getPos().distanceTo(position);
            if (distance > settings.renderRadius) {
                return false;
            }
        }
        
        // Reduce storm particle density
        return Math.random() < settings.stormParticleMultiplier * settings.density.getMultiplier();
    }
    
    private boolean shouldSpawnGeneralParticle(ParticleEffect particleType, Vec3d position) {
        String particleName = particleType.getClass().getSimpleName();
        
        // Count particles of this type
        int currentCount = particleCounts.getOrDefault(particleName, 0);
        if (currentCount >= settings.maxParticlesPerType) {
            return false;
        }
        
        // Distance-based culling
        if (client.player != null) {
            double distance = client.player.getPos().distanceTo(position);
            if (distance > settings.renderRadius * 2) {
                return false;
            }
        }
        
        // Field of view culling if enabled
        if (settings.cullOutOfView && settings.smartCulling) {
            if (!isInFieldOfView(position)) {
                return false;
            }
        }
        
        // Apply density multiplier
        boolean shouldSpawn = Math.random() < settings.density.getMultiplier();
        if (shouldSpawn) {
            particleCounts.put(particleName, currentCount + 1);
        }
        
        return shouldSpawn;
    }
    
    private boolean isInFieldOfView(Vec3d position) {
        if (client.player == null) return true;
        
        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();
        Vec3d cameraDirection = camera.getHorizontalPlane();
        
        Vec3d toParticle = position.subtract(cameraPos).normalize();
        double dot = cameraDirection.dotProduct(toParticle);
        
        // Consider particles within a 120-degree field of view
        return dot > -0.5;
    }
    
    /**
     * Gets the effective particle render distance based on settings
     */
    public float getEffectiveRenderDistance() {
        if (!isEnabled()) return Float.MAX_VALUE;
        return settings.renderRadius;
    }
    
    /**
     * Gets the particle density multiplier
     */
    public float getDensityMultiplier() {
        if (!isEnabled()) return 1.0f;
        return settings.density.getMultiplier();
    }
    
    @Override
    public boolean isEnabled() {
        return settings.enabled;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        settings.enabled = enabled;
    }
    
    @Override
    public String getName() {
        return "Particle Optimizer";
    }
    
    @Override
    public String getDescription() {
        return "Optimizes particle rendering to improve FPS, especially during storms";
    }
}