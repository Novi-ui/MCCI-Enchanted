package com.mccisland.enhanced.config;

import com.mccisland.enhanced.MCCIslandEnhancedClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> new ConfigScreen(parent);
    }
    
    private static class ConfigScreen extends Screen {
        private final Screen parent;
        private final ModConfig config;
        
        public ConfigScreen(Screen parent) {
            super(Text.literal("MCC Island Enhanced Configuration"));
            this.parent = parent;
            this.config = MCCIslandEnhancedClient.getInstance().getConfig();
        }
        
        @Override
        protected void init() {
            super.init();
            
            int buttonWidth = 200;
            int buttonHeight = 20;
            int spacing = 25;
            int startY = 50;
            
            // Main toggle
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Mod Enabled: " + (config.modEnabled ? "ON" : "OFF")),
                button -> {
                    config.modEnabled = !config.modEnabled;
                    button.setMessage(Text.literal("Mod Enabled: " + (config.modEnabled ? "ON" : "OFF")));
                    config.save();
                }
            ).dimensions(this.width / 2 - buttonWidth / 2, startY, buttonWidth, buttonHeight).build());
            
            // Profile selection
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Profile: " + config.currentProfile.getDisplayName()),
                button -> {
                    // Cycle through profiles
                    ModConfig.ConfigProfile[] profiles = ModConfig.ConfigProfile.values();
                    int currentIndex = 0;
                    for (int i = 0; i < profiles.length; i++) {
                        if (profiles[i] == config.currentProfile) {
                            currentIndex = i;
                            break;
                        }
                    }
                    int nextIndex = (currentIndex + 1) % profiles.length;
                    config.applyProfile(profiles[nextIndex]);
                    button.setMessage(Text.literal("Profile: " + config.currentProfile.getDisplayName()));
                }
            ).dimensions(this.width / 2 - buttonWidth / 2, startY + spacing, buttonWidth, buttonHeight).build());
            
            // Name Tags
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Name Tags: " + (config.nameTagSettings.enabled ? "ON" : "OFF")),
                button -> {
                    config.nameTagSettings.enabled = !config.nameTagSettings.enabled;
                    button.setMessage(Text.literal("Name Tags: " + (config.nameTagSettings.enabled ? "ON" : "OFF")));
                    config.save();
                }
            ).dimensions(this.width / 2 - buttonWidth / 2, startY + spacing * 2, buttonWidth, buttonHeight).build());
            
            // Ping System
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Ping System: " + (config.pingSettings.enabled ? "ON" : "OFF")),
                button -> {
                    config.pingSettings.enabled = !config.pingSettings.enabled;
                    button.setMessage(Text.literal("Ping System: " + (config.pingSettings.enabled ? "ON" : "OFF")));
                    config.save();
                }
            ).dimensions(this.width / 2 - buttonWidth / 2, startY + spacing * 3, buttonWidth, buttonHeight).build());
            
            // Particle Optimization
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Particles: " + config.particleSettings.density.getDisplayName()),
                button -> {
                    // Cycle through particle densities
                    ParticleSettings.ParticleDensity[] densities = ParticleSettings.ParticleDensity.values();
                    int currentIndex = 0;
                    for (int i = 0; i < densities.length; i++) {
                        if (densities[i] == config.particleSettings.density) {
                            currentIndex = i;
                            break;
                        }
                    }
                    int nextIndex = (currentIndex + 1) % densities.length;
                    config.particleSettings.density = densities[nextIndex];
                    button.setMessage(Text.literal("Particles: " + config.particleSettings.density.getDisplayName()));
                    config.save();
                }
            ).dimensions(this.width / 2 - buttonWidth / 2, startY + spacing * 4, buttonWidth, buttonHeight).build());
            
            // Game Features
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Sky Battle: " + (config.skyBattleSettings.enabled ? "ON" : "OFF")),
                button -> {
                    config.skyBattleSettings.enabled = !config.skyBattleSettings.enabled;
                    button.setMessage(Text.literal("Sky Battle: " + (config.skyBattleSettings.enabled ? "ON" : "OFF")));
                    config.save();
                }
            ).dimensions(this.width / 2 - buttonWidth / 2, startY + spacing * 5, buttonWidth, buttonHeight).build());
            
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Battle Box: " + (config.battleBoxSettings.enabled ? "ON" : "OFF")),
                button -> {
                    config.battleBoxSettings.enabled = !config.battleBoxSettings.enabled;
                    button.setMessage(Text.literal("Battle Box: " + (config.battleBoxSettings.enabled ? "ON" : "OFF")));
                    config.save();
                }
            ).dimensions(this.width / 2 - buttonWidth / 2, startY + spacing * 6, buttonWidth, buttonHeight).build());
            
            // HUD Position
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("HUD: " + config.hudSettings.position.getDisplayName()),
                button -> {
                    // Cycle through HUD positions
                    HudSettings.HudPosition[] positions = HudSettings.HudPosition.values();
                    int currentIndex = 0;
                    for (int i = 0; i < positions.length; i++) {
                        if (positions[i] == config.hudSettings.position) {
                            currentIndex = i;
                            break;
                        }
                    }
                    int nextIndex = (currentIndex + 1) % positions.length;
                    config.hudSettings.position = positions[nextIndex];
                    button.setMessage(Text.literal("HUD: " + config.hudSettings.position.getDisplayName()));
                    config.save();
                }
            ).dimensions(this.width / 2 - buttonWidth / 2, startY + spacing * 7, buttonWidth, buttonHeight).build());
            
            // Done button
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Done"),
                button -> this.client.setScreen(parent)
            ).dimensions(this.width / 2 - buttonWidth / 2, this.height - 30, buttonWidth, buttonHeight).build());
        }
        
        @Override
        public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
            this.renderBackground(context, mouseX, mouseY, delta);
            
            // Draw title
            context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
            
            // Draw description
            Text description = Text.literal("Configure MCC Island Enhanced features and settings");
            context.drawCenteredTextWithShadow(this.textRenderer, description, this.width / 2, 35, 0xAAAAAA);
            
            super.render(context, mouseX, mouseY, delta);
        }
        
        @Override
        public void close() {
            this.client.setScreen(parent);
        }
    }
}