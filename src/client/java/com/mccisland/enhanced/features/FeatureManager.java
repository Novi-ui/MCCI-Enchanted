package com.mccisland.enhanced.features;

import com.mccisland.enhanced.config.ModConfig;
import com.mccisland.enhanced.features.universal.NameTagRenderer;
import com.mccisland.enhanced.features.universal.PingSystem;
import com.mccisland.enhanced.features.universal.ParticleOptimizer;
import com.mccisland.enhanced.features.games.*;
import com.mccisland.enhanced.stubs.MinecraftStubs;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {
    private final ModConfig config;
    private final List<Feature> features;
    
    // Universal features
    private final NameTagRenderer nameTagRenderer;
    private final PingSystem pingSystem;
    private final ParticleOptimizer particleOptimizer;
    
    // Game-specific features
    private final SkyBattleFeature skyBattleFeature;
    private final BattleBoxFeature battleBoxFeature;
    private final TGTTOSFeature tgttosFeature;
    private final HoleInTheWallFeature holeInTheWallFeature;
    private final AceRaceFeature aceRaceFeature;
    private final ParkourWarriorFeature parkourWarriorFeature;
    
    public FeatureManager() {
        this.config = new ModConfig(); // Use default config during compilation
        this.features = new ArrayList<>();
        
        // Initialize universal features with safe defaults
        this.nameTagRenderer = new NameTagRenderer();
        this.pingSystem = new PingSystem();
        this.particleOptimizer = new ParticleOptimizer();
        
        // Initialize game-specific features with safe defaults
        this.skyBattleFeature = new SkyBattleFeature();
        this.battleBoxFeature = new BattleBoxFeature();
        this.tgttosFeature = new TGTTOSFeature();
        this.holeInTheWallFeature = new HoleInTheWallFeature();
        this.aceRaceFeature = new AceRaceFeature();
        this.parkourWarriorFeature = new ParkourWarriorFeature();
        
        // Add all features to the list
        features.add(nameTagRenderer);
        features.add(pingSystem);
        features.add(particleOptimizer);
        features.add(skyBattleFeature);
        features.add(battleBoxFeature);
        features.add(tgttosFeature);
        features.add(holeInTheWallFeature);
        features.add(aceRaceFeature);
        features.add(parkourWarriorFeature);
    }
    
    public void onClientTick() {
        if (!config.modEnabled) return;
        
        try {
            // Safe client access during runtime
            Class<?> clientClass = Class.forName("net.minecraft.client.MinecraftClient");
            Object client = clientClass.getMethod("getInstance").invoke(null);
            Object world = clientClass.getMethod("world").invoke(client);
            Object player = clientClass.getMethod("player").invoke(client);
            
            if (world == null || player == null) return;
            
            for (Feature feature : features) {
                if (feature.isEnabled()) {
                    feature.tick();
                }
            }
        } catch (Exception e) {
            // Fail silently during compilation or if Minecraft classes are not available
        }
    }
    
    public void tick() {
        onClientTick();
    }
    
    public void toggleAllModules() {
        boolean anyEnabled = features.stream().anyMatch(Feature::isEnabled);
        for (Feature feature : features) {
            feature.setEnabled(!anyEnabled);
        }
    }
    
    // Getters for individual features
    public NameTagRenderer getNameTagRenderer() {
        return nameTagRenderer;
    }
    
    public PingSystem getPingSystem() {
        return pingSystem;
    }
    
    public ParticleOptimizer getParticleOptimizer() {
        return particleOptimizer;
    }
    
    public SkyBattleFeature getSkyBattleFeature() {
        return skyBattleFeature;
    }
    
    public BattleBoxFeature getBattleBoxFeature() {
        return battleBoxFeature;
    }
    
    public TGTTOSFeature getTgttosFeature() {
        return tgttosFeature;
    }
    
    public HoleInTheWallFeature getHoleInTheWallFeature() {
        return holeInTheWallFeature;
    }
    
    public AceRaceFeature getAceRaceFeature() {
        return aceRaceFeature;
    }
    
    public ParkourWarriorFeature getParkourWarriorFeature() {
        return parkourWarriorFeature;
    }
    
    public List<Feature> getFeatures() {
        return features;
    }
}