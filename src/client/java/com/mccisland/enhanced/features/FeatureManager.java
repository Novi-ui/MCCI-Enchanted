package com.mccisland.enhanced.features;

import com.mccisland.enhanced.config.ModConfig;
import com.mccisland.enhanced.features.universal.NameTagRenderer;
import com.mccisland.enhanced.features.universal.PingSystem;
import com.mccisland.enhanced.features.universal.ParticleOptimizer;
import com.mccisland.enhanced.features.games.*;
import net.minecraft.client.MinecraftClient;

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
    
    public FeatureManager(ModConfig config) {
        this.config = config;
        this.features = new ArrayList<>();
        
        // Initialize universal features
        this.nameTagRenderer = new NameTagRenderer(config.nameTagSettings);
        this.pingSystem = new PingSystem(config.pingSettings);
        this.particleOptimizer = new ParticleOptimizer(config.particleSettings);
        
        // Initialize game-specific features
        this.skyBattleFeature = new SkyBattleFeature(config.skyBattleSettings);
        this.battleBoxFeature = new BattleBoxFeature(config.battleBoxSettings);
        this.tgttosFeature = new TGTTOSFeature(config.tgttosSettings);
        this.holeInTheWallFeature = new HoleInTheWallFeature(config.holeInTheWallSettings);
        this.aceRaceFeature = new AceRaceFeature(config.aceRaceSettings);
        this.parkourWarriorFeature = new ParkourWarriorFeature(config.parkourWarriorSettings);
        
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
    
    public void tick() {
        if (!config.modEnabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;
        
        for (Feature feature : features) {
            if (feature.isEnabled()) {
                feature.tick();
            }
        }
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