package com.mccisland.enhanced.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class RenderUtils {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    
    /**
     * Applies opacity to a color value
     */
    public static int applyOpacity(int color, float opacity) {
        int alpha = (int) (opacity * 255) & 0xFF;
        return (alpha << 24) | (color & 0x00FFFFFF);
    }
    
    /**
     * Renders a name tag at a world position
     */
    public static void renderNameTag(DrawContext context, Text text, Vec3d worldPos, float scale, int color) {
        renderNameTagInternal(context, text, worldPos, scale, color, false);
    }
    
    /**
     * Renders a name tag through walls at a world position
     */
    public static void renderNameTagThroughWalls(DrawContext context, Text text, Vec3d worldPos, float scale, int color) {
        renderNameTagInternal(context, text, worldPos, scale, color, true);
    }
    
    private static void renderNameTagInternal(DrawContext context, Text text, Vec3d worldPos, float scale, int color, boolean throughWalls) {
        if (client.player == null || client.gameRenderer == null) return;
        
        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
        Camera camera = client.gameRenderer.getCamera();
        
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        
        // Transform to world position
        Vec3d cameraPos = camera.getPos();
        matrices.translate(
            worldPos.x - cameraPos.x,
            worldPos.y - cameraPos.y,
            worldPos.z - cameraPos.z
        );
        
        // Face the camera
        matrices.multiply(dispatcher.getRotation());
        matrices.scale(-scale, -scale, scale);
        
        if (throughWalls) {
            // Disable depth testing to render through walls
            RenderSystem.disableDepthTest();
        }
        
        // Render background
        TextRenderer textRenderer = client.textRenderer;
        int textWidth = textRenderer.getWidth(text);
        int halfWidth = textWidth / 2;
        
        context.fill(-halfWidth - 2, -2, halfWidth + 2, 10, 0x80000000);
        
        // Render text
        context.drawText(textRenderer, text, -halfWidth, 0, color, false);
        
        if (throughWalls) {
            RenderSystem.enableDepthTest();
        }
        
        matrices.pop();
    }
    
    /**
     * Renders text at a world position
     */
    public static void renderTextAtPosition(DrawContext context, String text, Vec3d worldPos, float scale, int color) {
        renderNameTag(context, Text.literal(text), worldPos, scale, color);
    }
    
    /**
     * Renders a ping marker at a world position
     */
    public static void renderPingMarker(DrawContext context, Vec3d worldPos, float scale, int color) {
        if (client.player == null || client.gameRenderer == null) return;
        
        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
        Camera camera = client.gameRenderer.getCamera();
        
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        
        // Transform to world position
        Vec3d cameraPos = camera.getPos();
        matrices.translate(
            worldPos.x - cameraPos.x,
            worldPos.y - cameraPos.y,
            worldPos.z - cameraPos.z
        );
        
        // Face the camera
        matrices.multiply(dispatcher.getRotation());
        matrices.scale(scale, scale, scale);
        
        // Disable depth testing for ping markers
        RenderSystem.disableDepthTest();
        
        // Render a simple ping marker (circle with cross)
        VertexConsumerProvider.Immediate immediate = client.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vertexConsumer = immediate.getBuffer(RenderLayer.getLines());
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        
        // Draw circle
        int segments = 16;
        float radius = 0.5f;
        for (int i = 0; i < segments; i++) {
            float angle1 = (float) (2 * Math.PI * i / segments);
            float angle2 = (float) (2 * Math.PI * (i + 1) / segments);
            
            float x1 = (float) Math.cos(angle1) * radius;
            float y1 = (float) Math.sin(angle1) * radius;
            float x2 = (float) Math.cos(angle2) * radius;
            float y2 = (float) Math.sin(angle2) * radius;
            
            vertexConsumer.vertex(matrix, x1, y1, 0).color(color).normal(0, 0, 1).next();
            vertexConsumer.vertex(matrix, x2, y2, 0).color(color).normal(0, 0, 1).next();
        }
        
        // Draw cross
        vertexConsumer.vertex(matrix, -radius, 0, 0).color(color).normal(0, 0, 1).next();
        vertexConsumer.vertex(matrix, radius, 0, 0).color(color).normal(0, 0, 1).next();
        vertexConsumer.vertex(matrix, 0, -radius, 0).color(color).normal(0, 0, 1).next();
        vertexConsumer.vertex(matrix, 0, radius, 0).color(color).normal(0, 0, 1).next();
        
        immediate.draw();
        
        RenderSystem.enableDepthTest();
        matrices.pop();
    }
    
    /**
     * Renders a line between two world positions
     */
    public static void renderLine(DrawContext context, Vec3d start, Vec3d end, int color, float width) {
        if (client.player == null || client.gameRenderer == null) return;
        
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        
        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();
        
        VertexConsumerProvider.Immediate immediate = client.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vertexConsumer = immediate.getBuffer(RenderLayer.getLines());
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        
        RenderSystem.lineWidth(width);
        
        vertexConsumer.vertex(matrix, 
            (float)(start.x - cameraPos.x),
            (float)(start.y - cameraPos.y),
            (float)(start.z - cameraPos.z)
        ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix,
            (float)(end.x - cameraPos.x),
            (float)(end.y - cameraPos.y),
            (float)(end.z - cameraPos.z)
        ).color(color).normal(0, 1, 0).next();
        
        immediate.draw();
        
        RenderSystem.lineWidth(1.0f);
        matrices.pop();
    }
    
    /**
     * Renders a box outline at a world position
     */
    public static void renderBox(DrawContext context, Vec3d pos, Vec3d size, int color) {
        renderBoxInternal(context, pos, size, color, false);
    }
    
    /**
     * Renders a filled box at a world position
     */
    public static void renderFilledBox(DrawContext context, Vec3d pos, Vec3d size, int color) {
        renderBoxInternal(context, pos, size, color, true);
    }
    
    private static void renderBoxInternal(DrawContext context, Vec3d pos, Vec3d size, int color, boolean filled) {
        if (client.player == null || client.gameRenderer == null) return;
        
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        
        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();
        
        matrices.translate(
            pos.x - cameraPos.x,
            pos.y - cameraPos.y,
            pos.z - cameraPos.z
        );
        
        VertexConsumerProvider.Immediate immediate = client.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vertexConsumer = immediate.getBuffer(
            filled ? RenderLayer.getTranslucent() : RenderLayer.getLines()
        );
        
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        
        float minX = (float) (-size.x / 2);
        float minY = (float) (-size.y / 2);
        float minZ = (float) (-size.z / 2);
        float maxX = (float) (size.x / 2);
        float maxY = (float) (size.y / 2);
        float maxZ = (float) (size.z / 2);
        
        if (filled) {
            // Render filled box faces
            renderBoxFace(vertexConsumer, matrix, minX, minY, minZ, maxX, minY, maxZ, color); // Bottom
            renderBoxFace(vertexConsumer, matrix, minX, maxY, minZ, maxX, maxY, maxZ, color); // Top
            renderBoxFace(vertexConsumer, matrix, minX, minY, minZ, minX, maxY, maxZ, color); // West
            renderBoxFace(vertexConsumer, matrix, maxX, minY, minZ, maxX, maxY, maxZ, color); // East
            renderBoxFace(vertexConsumer, matrix, minX, minY, minZ, maxX, maxY, minZ, color); // North
            renderBoxFace(vertexConsumer, matrix, minX, minY, maxZ, maxX, maxY, maxZ, color); // South
        } else {
            // Render box outline
            renderBoxEdges(vertexConsumer, matrix, minX, minY, minZ, maxX, maxY, maxZ, color);
        }
        
        immediate.draw();
        matrices.pop();
    }
    
    private static void renderBoxFace(VertexConsumer vertexConsumer, Matrix4f matrix, 
                                    float x1, float y1, float z1, float x2, float y2, float z2, int color) {
        // This is a simplified implementation - in practice you'd need to properly define the face vertices
        vertexConsumer.vertex(matrix, x1, y1, z1).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, x2, y1, z1).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, x2, y2, z2).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, x1, y2, z2).color(color).normal(0, 1, 0).next();
    }
    
    private static void renderBoxEdges(VertexConsumer vertexConsumer, Matrix4f matrix,
                                     float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int color) {
        // Bottom edges
        vertexConsumer.vertex(matrix, minX, minY, minZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, minX, minY, minZ).color(color).normal(0, 1, 0).next();
        
        // Top edges
        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(color).normal(0, 1, 0).next();
        
        // Vertical edges
        vertexConsumer.vertex(matrix, minX, minY, minZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(color).normal(0, 1, 0).next();
        
        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(color).normal(0, 1, 0).next();
        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(color).normal(0, 1, 0).next();
    }
}