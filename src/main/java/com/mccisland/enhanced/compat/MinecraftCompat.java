package com.mccisland.enhanced.compat;

import com.mccisland.enhanced.util.MinecraftVersionUtil;

/**
 * Compatibility layer for different Minecraft versions
 */
public class MinecraftCompat {
    
    private static final MinecraftVersionUtil.MinecraftVersion CURRENT_VERSION = MinecraftVersionUtil.getCurrentVersion();
    
    /**
     * Gets the appropriate method name for rendering text based on the current version
     * @return The method name to use
     */
    public static String getDrawTextMethodName() {
        if (CURRENT_VERSION.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_20_0)) {
            return "drawText";
        } else {
            return "drawTextWithShadow";
        }
    }
    
    /**
     * Gets the appropriate class name for the HUD renderer based on the current version
     * @return The class name to use
     */
    public static String getHudRendererClassName() {
        if (CURRENT_VERSION.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_20_0)) {
            return "net.minecraft.client.gui.hud.InGameHud";
        } else {
            return "net.minecraft.client.gui.hud.InGameHud";
        }
    }
    
    /**
     * Gets the appropriate method name for particle spawning based on the current version
     * @return The method name to use
     */
    public static String getParticleSpawnMethodName() {
        if (CURRENT_VERSION.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_20_0)) {
            return "addParticle";
        } else {
            return "addParticle";
        }
    }
    
    /**
     * Checks if the current version supports a specific feature
     * @param feature The feature to check
     * @return true if supported, false otherwise
     */
    public static boolean supportsFeature(CompatFeature feature) {
        switch (feature) {
            case MODERN_RENDERING:
                return CURRENT_VERSION.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_20_0);
            case PARTICLE_OPTIMIZATION:
                return CURRENT_VERSION.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_19_4);
            case ADVANCED_HUD:
                return CURRENT_VERSION.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_20_0);
            case KEYBINDING_SYSTEM:
                return CURRENT_VERSION.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_19_4);
            default:
                return false;
        }
    }
    
    /**
     * Gets the appropriate mixin target class based on the current version
     * @param mixinType The type of mixin
     * @return The target class name
     */
    public static String getMixinTarget(MixinType mixinType) {
        switch (mixinType) {
            case PARTICLE_MANAGER:
                return "net.minecraft.client.particle.ParticleManager";
            case GAME_RENDERER:
                return "net.minecraft.client.render.GameRenderer";
            case IN_GAME_HUD:
                return "net.minecraft.client.gui.hud.InGameHud";
            case CLIENT_PLAYER_ENTITY:
                if (CURRENT_VERSION.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_20_0)) {
                    return "net.minecraft.client.network.ClientPlayerEntity";
                } else {
                    return "net.minecraft.client.network.ClientPlayerEntity";
                }
            case WORLD_RENDERER:
                return "net.minecraft.client.render.WorldRenderer";
            default:
                return "";
        }
    }
    
    /**
     * Features that may or may not be available in different versions
     */
    public enum CompatFeature {
        MODERN_RENDERING,
        PARTICLE_OPTIMIZATION,
        ADVANCED_HUD,
        KEYBINDING_SYSTEM
    }
    
    /**
     * Types of mixins used in the mod
     */
    public enum MixinType {
        PARTICLE_MANAGER,
        GAME_RENDERER,
        IN_GAME_HUD,
        CLIENT_PLAYER_ENTITY,
        WORLD_RENDERER
    }
}