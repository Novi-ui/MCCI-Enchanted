package net.minecraft.client.render;

/**
 * Stub class for VertexConsumer
 */
public interface VertexConsumer {
    
    VertexConsumer vertex(double x, double y, double z);
    
    VertexConsumer color(int red, int green, int blue, int alpha);
    
    VertexConsumer texture(float u, float v);
    
    VertexConsumer overlay(int u, int v);
    
    VertexConsumer light(int uv);
    
    VertexConsumer normal(float x, float y, float z);
    
    void next();
}