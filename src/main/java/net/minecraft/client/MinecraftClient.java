package net.minecraft.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.font.TextRenderer;

/**
 * Stub class for MinecraftClient
 */
public class MinecraftClient {
    private static MinecraftClient instance;
    
    public static MinecraftClient getInstance() {
        if (instance == null) {
            instance = new MinecraftClient();
        }
        return instance;
    }
    
    public Object getWindow() {
        return new Object();
    }
    
    public TextRenderer textRenderer() {
        return new TextRenderer();
    }
    
    public ClientPlayerEntity player() {
        return new ClientPlayerEntity();
    }
    
    public Object world() {
        return new Object();
    }
    
    public GameRenderer gameRenderer() {
        return new GameRenderer();
    }
    
    public EntityRenderDispatcher getEntityRenderDispatcher() {
        return new EntityRenderDispatcher();
    }
    
    public Object getBufferBuilders() {
        return new Object();
    }
    
    public Object crosshairTarget() {
        return new Object();
    }
}