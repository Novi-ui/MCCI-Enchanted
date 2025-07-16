package com.mccisland.enhanced.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mccisland.enhanced.MCCIslandEnhancedClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("mcc-island-enhanced.json");
    
    // General settings
    public boolean modEnabled = true;
    public ConfigProfile currentProfile = ConfigProfile.COMPETITIVE;
    
    // Name tag settings
    public NameTagSettings nameTagSettings = new NameTagSettings();
    
    // Ping system settings
    public PingSettings pingSettings = new PingSettings();
    
    // Particle optimization settings
    public ParticleSettings particleSettings = new ParticleSettings();
    
    // Game-specific settings
    public SkyBattleSettings skyBattleSettings = new SkyBattleSettings();
    public BattleBoxSettings battleBoxSettings = new BattleBoxSettings();
    public TGTTOSSettings tgttosSettings = new TGTTOSSettings();
    public HoleInTheWallSettings holeInTheWallSettings = new HoleInTheWallSettings();
    public AceRaceSettings aceRaceSettings = new AceRaceSettings();
    public ParkourWarriorSettings parkourWarriorSettings = new ParkourWarriorSettings();
    
    // HUD settings
    public HudSettings hudSettings = new HudSettings();
    
    public void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                ModConfig loaded = GSON.fromJson(json, ModConfig.class);
                copyFrom(loaded);
                MCCIslandEnhancedClient.LOGGER.info("Configuration loaded successfully");
            } catch (Exception e) {
                MCCIslandEnhancedClient.LOGGER.error("Failed to load configuration: " + e.getMessage());
                save(); // Save default config
            }
        } else {
            save(); // Create default config
        }
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(this);
            Files.writeString(CONFIG_PATH, json);
            MCCIslandEnhancedClient.LOGGER.info("Configuration saved successfully");
        } catch (IOException e) {
            MCCIslandEnhancedClient.LOGGER.error("Failed to save configuration: " + e.getMessage());
        }
    }
    
    private void copyFrom(ModConfig other) {
        this.modEnabled = other.modEnabled;
        this.currentProfile = other.currentProfile;
        this.nameTagSettings = other.nameTagSettings != null ? other.nameTagSettings : new NameTagSettings();
        this.pingSettings = other.pingSettings != null ? other.pingSettings : new PingSettings();
        this.particleSettings = other.particleSettings != null ? other.particleSettings : new ParticleSettings();
        this.skyBattleSettings = other.skyBattleSettings != null ? other.skyBattleSettings : new SkyBattleSettings();
        this.battleBoxSettings = other.battleBoxSettings != null ? other.battleBoxSettings : new BattleBoxSettings();
        this.tgttosSettings = other.tgttosSettings != null ? other.tgttosSettings : new TGTTOSSettings();
        this.holeInTheWallSettings = other.holeInTheWallSettings != null ? other.holeInTheWallSettings : new HoleInTheWallSettings();
        this.aceRaceSettings = other.aceRaceSettings != null ? other.aceRaceSettings : new AceRaceSettings();
        this.parkourWarriorSettings = other.parkourWarriorSettings != null ? other.parkourWarriorSettings : new ParkourWarriorSettings();
        this.hudSettings = other.hudSettings != null ? other.hudSettings : new HudSettings();
    }
    
    public void applyProfile(ConfigProfile profile) {
        this.currentProfile = profile;
        switch (profile) {
            case COMPETITIVE -> applyCompetitiveSettings();
            case CASUAL -> applyCasualSettings();
            case STREAMER -> applyStreamerSettings();
            case PERFORMANCE -> applyPerformanceSettings();
            case CUSTOM -> {} // Don't change anything for custom
        }
        save();
    }
    
    private void applyCompetitiveSettings() {
        nameTagSettings.enabled = true;
        nameTagSettings.renderDistance = 64.0f;
        nameTagSettings.showEnemies = true;
        
        pingSettings.enabled = true;
        
        particleSettings.enabled = true;
        particleSettings.density = ParticleSettings.ParticleDensity.LOW;
        
        skyBattleSettings.enabled = true;
        battleBoxSettings.enabled = true;
        tgttosSettings.enabled = true;
        holeInTheWallSettings.enabled = true;
        aceRaceSettings.enabled = true;
        parkourWarriorSettings.enabled = true;
    }
    
    private void applyCasualSettings() {
        nameTagSettings.enabled = true;
        nameTagSettings.renderDistance = 32.0f;
        nameTagSettings.showEnemies = false;
        
        pingSettings.enabled = false;
        
        particleSettings.enabled = true;
        particleSettings.density = ParticleSettings.ParticleDensity.MEDIUM;
        
        skyBattleSettings.enabled = false;
        battleBoxSettings.enabled = false;
        tgttosSettings.enabled = true;
        holeInTheWallSettings.enabled = true;
        aceRaceSettings.enabled = true;
        parkourWarriorSettings.enabled = true;
    }
    
    private void applyStreamerSettings() {
        nameTagSettings.enabled = true;
        nameTagSettings.renderDistance = 48.0f;
        nameTagSettings.showEnemies = true;
        
        pingSettings.enabled = false; // Avoid showing too much tactical info on stream
        
        particleSettings.enabled = true;
        particleSettings.density = ParticleSettings.ParticleDensity.HIGH;
        
        skyBattleSettings.enabled = true;
        battleBoxSettings.enabled = true;
        tgttosSettings.enabled = true;
        holeInTheWallSettings.enabled = true;
        aceRaceSettings.enabled = true;
        parkourWarriorSettings.enabled = true;
    }
    
    private void applyPerformanceSettings() {
        nameTagSettings.enabled = true;
        nameTagSettings.renderDistance = 24.0f;
        nameTagSettings.showEnemies = false;
        
        pingSettings.enabled = false;
        
        particleSettings.enabled = true;
        particleSettings.density = ParticleSettings.ParticleDensity.OFF;
        
        skyBattleSettings.enabled = false;
        battleBoxSettings.enabled = false;
        tgttosSettings.enabled = false;
        holeInTheWallSettings.enabled = false;
        aceRaceSettings.enabled = false;
        parkourWarriorSettings.enabled = false;
    }
    
    public enum ConfigProfile {
        COMPETITIVE("Competitive"),
        CASUAL("Casual"),
        STREAMER("Streamer"),
        PERFORMANCE("Performance"),
        CUSTOM("Custom");
        
        private final String displayName;
        
        ConfigProfile(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}