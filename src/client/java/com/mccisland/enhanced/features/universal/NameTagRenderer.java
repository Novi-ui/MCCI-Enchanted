package com.mccisland.enhanced.features.universal;

import com.mccisland.enhanced.config.NameTagSettings;
import com.mccisland.enhanced.features.Feature;
import com.mccisland.enhanced.util.RenderUtils;
import com.mccisland.enhanced.util.TeamUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.stream.Collectors;

public class NameTagRenderer implements Feature {
    private final NameTagSettings settings;
    private final MinecraftClient client;
    
    public NameTagRenderer(NameTagSettings settings) {
        this.settings = settings;
        this.client = MinecraftClient.getInstance();
    }
    
    @Override
    public void tick() {
        // Update logic handled in rendering
    }
    
    @Override
    public void renderWorld(DrawContext context, float tickDelta) {
        if (!isEnabled() || client.world == null || client.player == null) {
            return;
        }
        
        ClientPlayerEntity player = client.player;
        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();
        
        // Get all players within render distance
        List<PlayerEntity> nearbyPlayers = client.world.getEntitiesByClass(
            PlayerEntity.class,
            player.getBoundingBox().expand(settings.renderDistance),
            p -> p != player && p.squaredDistanceTo(player) <= settings.renderDistance * settings.renderDistance
        ).stream()
        .limit(settings.maxPlayersToRender)
        .collect(Collectors.toList());
        
        for (PlayerEntity targetPlayer : nearbyPlayers) {
            if (!shouldRenderNameTag(targetPlayer)) {
                continue;
            }
            
            Vec3d playerPos = targetPlayer.getPos().add(0, targetPlayer.getHeight() + 0.5, 0);
            double distance = cameraPos.distanceTo(playerPos);
            
            // Calculate opacity based on distance
            float opacity = 1.0f;
            if (settings.distanceBasedOpacity) {
                opacity = Math.max(0.2f, 1.0f - (float)(distance / settings.renderDistance));
            }
            
            // Get team color
            int color = getPlayerNameColor(targetPlayer);
            
            // Render the name tag
            renderNameTag(context, targetPlayer, playerPos, distance, opacity, color);
        }
    }
    
    private boolean shouldRenderNameTag(PlayerEntity player) {
        if (!settings.showEnemies && !TeamUtils.isTeammate(player)) {
            return false;
        }
        
        if (!settings.showTeammates && TeamUtils.isTeammate(player)) {
            return false;
        }
        
        return true;
    }
    
    private int getPlayerNameColor(PlayerEntity player) {
        if (settings.teamColoredNames) {
            return TeamUtils.getTeamColor(player);
        }
        return 0xFFFFFF; // White default
    }
    
    private void renderNameTag(DrawContext context, PlayerEntity player, Vec3d pos, double distance, float opacity, int color) {
        String name = player.getGameProfile().getName();
        
        // Add health info if enabled
        if (settings.showHealthBar) {
            float health = player.getHealth();
            float maxHealth = player.getMaxHealth();
            name += String.format(" %.1f/%.1f", health, maxHealth);
        }
        
        Text nameText = Text.literal(name);
        
        // Calculate scale based on distance and settings
        float scale = settings.nameScale;
        if (distance > 20) {
            scale *= Math.max(0.5f, 20.0f / (float)distance);
        }
        
        // Apply opacity to color
        int finalColor = RenderUtils.applyOpacity(color, opacity);
        
        // Render the name tag through walls if enabled
        if (settings.showThroughWalls) {
            RenderUtils.renderNameTagThroughWalls(context, nameText, pos, scale, finalColor);
        } else {
            RenderUtils.renderNameTag(context, nameText, pos, scale, finalColor);
        }
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
        return "Name Tag Renderer";
    }
    
    @Override
    public String getDescription() {
        return "Renders player names through walls with team colors and distance-based opacity";
    }
}