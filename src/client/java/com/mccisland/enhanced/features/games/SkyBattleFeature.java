package com.mccisland.enhanced.features.games;

import com.mccisland.enhanced.config.SkyBattleSettings;
import com.mccisland.enhanced.features.Feature;
import com.mccisland.enhanced.util.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec3d;

public class SkyBattleFeature implements Feature {
    private final SkyBattleSettings settings;
    private final MinecraftClient client;
    
    // Storm tracking
    private Vec3d stormCenter = new Vec3d(0, 0, 0);
    private float stormRadius = 100.0f;
    private float stormSpeed = 0.5f;
    private Vec3d stormDirection = new Vec3d(0, 0, 0);
    private long stormStartTime = 0;
    private int stormPhase = 0;
    
    public SkyBattleFeature(SkyBattleSettings settings) {
        this.settings = settings;
        this.client = MinecraftClient.getInstance();
    }
    
    @Override
    public void tick() {
        if (!isEnabled() || client.world == null || client.player == null) return;
        
        updateStormTracking();
    }
    
    @Override
    public void renderHud(DrawContext context, float tickDelta) {
        if (!isEnabled() || client.player == null) return;
        
        int y = 10;
        
        if (settings.showStormTimer) {
            long elapsed = System.currentTimeMillis() - stormStartTime;
            String timeText = String.format("Storm Time: %d:%02d", 
                elapsed / 60000, (elapsed % 60000) / 1000);
            context.drawText(client.textRenderer, timeText, 10, y, 0xFFFFFF, true);
            y += 12;
        }
        
        if (settings.showDistanceToStorm && client.player != null) {
            double distance = client.player.getPos().distanceTo(stormCenter) - stormRadius;
            String distanceText = String.format("Distance to Storm: %.1fm", Math.max(0, distance));
            int color = distance < 10 ? 0xFF0000 : distance < 30 ? 0xFFFF00 : 0x00FF00;
            context.drawText(client.textRenderer, distanceText, 10, y, color, true);
            y += 12;
        }
        
        if (settings.showStormDirection) {
            String directionText = String.format("Storm Direction: %.0f° Speed: %.1f", 
                getStormDirectionAngle(), stormSpeed);
            context.drawText(client.textRenderer, directionText, 10, y, 0xAAAAAAA, true);
            y += 12;
        }
    }
    
    @Override
    public void renderWorld(DrawContext context, float tickDelta) {
        if (!isEnabled() || client.player == null) return;
        
        // Render storm zone
        if (settings.showSafeZone) {
            renderStormZone(context);
        }
        
        // Render next circle prediction
        if (settings.predictNextCircle) {
            renderNextCirclePrediction(context);
        }
        
        // Render route optimization
        if (settings.showRouteOptimization) {
            renderOptimalRoute(context);
        }
    }
    
    private void updateStormTracking() {
        // This would be updated based on actual game data
        // For now, simulate storm movement
        if (stormStartTime == 0) {
            stormStartTime = System.currentTimeMillis();
        }
        
        long elapsed = System.currentTimeMillis() - stormStartTime;
        
        // Simulate storm phases
        if (elapsed > 60000 && stormPhase == 0) { // 1 minute
            stormPhase = 1;
            stormRadius = 75.0f;
            stormSpeed = 0.7f;
        } else if (elapsed > 120000 && stormPhase == 1) { // 2 minutes
            stormPhase = 2;
            stormRadius = 50.0f;
            stormSpeed = 1.0f;
        } else if (elapsed > 180000 && stormPhase == 2) { // 3 minutes
            stormPhase = 3;
            stormRadius = 25.0f;
            stormSpeed = 1.5f;
        }
        
        // Update storm position
        stormCenter = stormCenter.add(stormDirection.multiply(stormSpeed * 0.05));
    }
    
    private void renderStormZone(DrawContext context) {
        if (stormRadius <= 0) return;
        
        // Render storm boundary
        int color = RenderUtils.applyOpacity(0xFF0000, settings.stormOpacity);
        
        // This is a simplified circle render - in practice you'd want a proper circle
        for (int i = 0; i < 32; i++) {
            double angle1 = 2 * Math.PI * i / 32;
            double angle2 = 2 * Math.PI * (i + 1) / 32;
            
            Vec3d pos1 = stormCenter.add(
                Math.cos(angle1) * stormRadius,
                0,
                Math.sin(angle1) * stormRadius
            );
            
            Vec3d pos2 = stormCenter.add(
                Math.cos(angle2) * stormRadius,
                0,
                Math.sin(angle2) * stormRadius
            );
            
            RenderUtils.renderLine(context, pos1, pos2, color, 2.0f);
        }
    }
    
    private void renderNextCirclePrediction(DrawContext context) {
        if (stormPhase >= 3) return; // No more circles
        
        float nextRadius = getNextCircleRadius();
        Vec3d nextCenter = predictNextCenter();
        
        int color = RenderUtils.applyOpacity(0x00FF00, 0.5f);
        
        // Render predicted next circle
        for (int i = 0; i < 32; i++) {
            double angle1 = 2 * Math.PI * i / 32;
            double angle2 = 2 * Math.PI * (i + 1) / 32;
            
            Vec3d pos1 = nextCenter.add(
                Math.cos(angle1) * nextRadius,
                0,
                Math.sin(angle1) * nextRadius
            );
            
            Vec3d pos2 = nextCenter.add(
                Math.cos(angle2) * nextRadius,
                0,
                Math.sin(angle2) * nextRadius
            );
            
            RenderUtils.renderLine(context, pos1, pos2, color, 1.0f);
        }
    }
    
    private void renderOptimalRoute(DrawContext context) {
        if (client.player == null) return;
        
        Vec3d playerPos = client.player.getPos();
        Vec3d optimalPos = findOptimalPosition();
        
        if (optimalPos != null) {
            // Render line to optimal position
            RenderUtils.renderLine(context, playerPos, optimalPos, 0x00FFFF, 2.0f);
            
            // Render marker at optimal position
            RenderUtils.renderTextAtPosition(context, "Optimal Position", 
                optimalPos.add(0, 2, 0), 1.0f, 0x00FFFF);
        }
    }
    
    private float getNextCircleRadius() {
        return switch (stormPhase) {
            case 0 -> 75.0f;
            case 1 -> 50.0f;
            case 2 -> 25.0f;
            default -> 0.0f;
        };
    }
    
    private Vec3d predictNextCenter() {
        // Simple prediction - in practice this would use game data
        return stormCenter.add(stormDirection.multiply(20));
    }
    
    private Vec3d findOptimalPosition() {
        if (client.player == null) return null;
        
        Vec3d playerPos = client.player.getPos();
        double distanceToStorm = playerPos.distanceTo(stormCenter) - stormRadius;
        
        if (distanceToStorm > 10) {
            // Move towards center of safe zone
            Vec3d direction = stormCenter.subtract(playerPos).normalize();
            return playerPos.add(direction.multiply(Math.min(distanceToStorm - 5, 20)));
        }
        
        return null;
    }
    
    private double getStormDirectionAngle() {
        double angle = Math.atan2(stormDirection.z, stormDirection.x);
        return Math.toDegrees(angle);
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
        return "Sky Battle";
    }
    
    @Override
    public String getDescription() {
        return "Provides storm tracking, safe zone overlay, and tactical information for Sky Battle";
    }
}