package com.mccisland.enhanced.render;

import com.mccisland.enhanced.config.HudSettings;
import com.mccisland.enhanced.config.ModConfig;
import com.mccisland.enhanced.features.Feature;
import com.mccisland.enhanced.features.FeatureManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class HudRenderer {
    private final ModConfig config;
    private final FeatureManager featureManager;
    private final MinecraftClient client;
    
    public HudRenderer(ModConfig config, FeatureManager featureManager) {
        this.config = config;
        this.featureManager = featureManager;
        this.client = MinecraftClient.getInstance();
    }
    
    public void render(DrawContext context, float tickDelta) {
        if (!config.modEnabled || !config.hudSettings.enabled) return;
        if (client.options.debugEnabled) return; // Hide when F3 is open
        
        HudSettings settings = config.hudSettings;
        
        // Calculate position
        int x = settings.xOffset;
        int y = settings.yOffset;
        
        // Adjust position based on HUD position setting
        if (settings.position == HudSettings.HudPosition.TOP_RIGHT) {
            x = context.getScaledWindowWidth() - settings.xOffset - 200;
        } else if (settings.position == HudSettings.HudPosition.BOTTOM_LEFT) {
            y = context.getScaledWindowHeight() - settings.yOffset - 100;
        } else if (settings.position == HudSettings.HudPosition.BOTTOM_RIGHT) {
            x = context.getScaledWindowWidth() - settings.xOffset - 200;
            y = context.getScaledWindowHeight() - settings.yOffset - 100;
        } else if (settings.position == HudSettings.HudPosition.CENTER) {
            x = context.getScaledWindowWidth() / 2 - 100;
            y = context.getScaledWindowHeight() / 2 - 50;
        }
        
        // Scale the rendering
        context.getMatrices().push();
        context.getMatrices().scale(settings.scale, settings.scale, settings.scale);
        
        // Adjust coordinates for scaling
        x = (int) (x / settings.scale);
        y = (int) (y / settings.scale);
        
        // Render HUD elements
        renderHudContent(context, x, y, settings);
        
        context.getMatrices().pop();
        
        // Render world overlays for features
        for (Feature feature : featureManager.getFeatures()) {
            if (feature.isEnabled()) {
                feature.renderWorld(context, tickDelta);
            }
        }
    }
    
    private void renderHudContent(DrawContext context, int x, int y, HudSettings settings) {
        List<String> hudLines = new ArrayList<>();
        
        // Add mod status
        hudLines.add("§eMCC Island Enhanced");
        hudLines.add("§7Mod: " + (config.modEnabled ? "§aEnabled" : "§cDisabled"));
        hudLines.add("§7Profile: §b" + config.currentProfile.getDisplayName());
        
        // Add feature status
        if (config.nameTagSettings.enabled) {
            hudLines.add("§7Name Tags: §aOn");
        }
        
        if (config.pingSettings.enabled) {
            hudLines.add("§7Ping System: §aOn");
        }
        
        if (config.particleSettings.enabled) {
            hudLines.add("§7Particles: §a" + config.particleSettings.density.getDisplayName());
        }
        
        // Add game-specific info
        addGameSpecificInfo(hudLines);
        
        // Render background if enabled
        if (settings.showBackground) {
            int maxWidth = hudLines.stream()
                .mapToInt(line -> client.textRenderer.getWidth(line))
                .max()
                .orElse(0);
            
            int height = hudLines.size() * 10 + 4;
            int backgroundColor = (int) (settings.backgroundOpacity * 255) << 24;
            
            context.fill(x - 2, y - 2, x + maxWidth + 2, y + height, backgroundColor);
        }
        
        // Render text lines
        TextRenderer textRenderer = client.textRenderer;
        for (int i = 0; i < hudLines.size(); i++) {
            String line = hudLines.get(i);
            int color = getTextColor(line, settings);
            
            if (settings.largeText) {
                context.getMatrices().push();
                context.getMatrices().scale(1.2f, 1.2f, 1.2f);
                context.drawText(textRenderer, Text.literal(line), 
                    (int) ((x) / 1.2f), (int) ((y + i * 12) / 1.2f), color, false);
                context.getMatrices().pop();
            } else {
                context.drawText(textRenderer, Text.literal(line), x, y + i * 10, color, false);
            }
        }
    }
    
    private void addGameSpecificInfo(List<String> hudLines) {
        // Sky Battle info
        if (config.skyBattleSettings.enabled) {
            // TODO: Add sky battle specific HUD info
            hudLines.add("§7Sky Battle: §aActive");
        }
        
        // Battle Box info
        if (config.battleBoxSettings.enabled) {
            // TODO: Add battle box specific HUD info
            hudLines.add("§7Battle Box: §aActive");
        }
        
        // TGTTOS info
        if (config.tgttosSettings.enabled) {
            // TODO: Add TGTTOS specific HUD info
            hudLines.add("§7TGTTOS: §aActive");
        }
        
        // Other games...
    }
    
    private int getTextColor(String line, HudSettings settings) {
        if (settings.rainbowText) {
            // Simple rainbow effect based on time
            long time = System.currentTimeMillis();
            float hue = (time % 3000) / 3000.0f;
            return java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
        }
        
        if (settings.highContrast) {
            if (line.contains("§a")) return 0x00FF00; // Bright green
            if (line.contains("§c")) return 0xFF0000; // Bright red
            if (line.contains("§e")) return 0xFFFF00; // Bright yellow
            if (line.contains("§b")) return 0x00FFFF; // Bright cyan
            if (line.contains("§7")) return 0xAAAAAA; // Light gray
            return 0xFFFFFF; // White
        }
        
        return 0xFFFFFF; // Default white
    }
}