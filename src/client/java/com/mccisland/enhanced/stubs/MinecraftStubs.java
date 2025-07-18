package com.mccisland.enhanced.stubs;

/**
 * Stub classes for Minecraft classes that are not available during compilation
 * These are only used for compilation and will be replaced at runtime
 */
public class MinecraftStubs {
    
    // Minecraft Client stub
    public static class MinecraftClient {
        public static MinecraftClient getInstance() { return null; }
        public Object getTextRenderer() { return null; }
        public Object player() { return null; }
        public Object world() { return null; }
        public Object getWindow() { return null; }
    }
    
    // Drawing context stub
    public static class DrawContext {
        public void drawText(Object textRenderer, String text, int x, int y, int color) {}
        public void drawTextWithShadow(Object textRenderer, String text, int x, int y, int color) {}
        public void fill(int x1, int y1, int x2, int y2, int color) {}
        public void drawBorder(int x, int y, int width, int height, int color) {}
    }
    
    // Text stub
    public static class Text {
        public static Text literal(String text) { return new Text(); }
        public static Text of(String text) { return new Text(); }
        public String getString() { return ""; }
    }
    
    // Vector3D stub
    public static class Vec3d {
        public double x, y, z;
        public Vec3d(double x, double y, double z) { this.x = x; this.y = y; this.z = z; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getZ() { return z; }
        public Vec3d add(double x, double y, double z) { return new Vec3d(this.x + x, this.y + y, this.z + z); }
        public Vec3d subtract(Vec3d other) { return new Vec3d(x - other.x, y - other.y, z - other.z); }
        public double distanceTo(Vec3d other) { return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2)); }
        public double length() { return Math.sqrt(x * x + y * y + z * z); }
    }
    
    // Particle Effect stub
    public static class ParticleEffect {
        public String getType() { return ""; }
    }
    
    // Player Entity stub
    public static class PlayerEntity {
        public String getName() { return ""; }
        public Vec3d getPos() { return new Vec3d(0, 0, 0); }
        public boolean isSpectator() { return false; }
        public boolean isInvisible() { return false; }
    }
    
    // Client Player Entity stub
    public static class ClientPlayerEntity extends PlayerEntity {
        public void sendMessage(Text message) {}
        public double getX() { return 0; }
        public double getY() { return 0; }
        public double getZ() { return 0; }
    }
    
    // Entity stub
    public static class Entity {
        public Vec3d getPos() { return new Vec3d(0, 0, 0); }
        public String getName() { return ""; }
        public boolean isAlive() { return true; }
    }
    
    // Hit Result stub
    public static class HitResult {
        public Vec3d getPos() { return new Vec3d(0, 0, 0); }
        public Type getType() { return Type.MISS; }
        public enum Type { MISS, BLOCK, ENTITY }
    }
    
    // Block Hit Result stub
    public static class BlockHitResult extends HitResult {
        public Object getBlockPos() { return null; }
        public Object getSide() { return null; }
    }
    
    // Entity Hit Result stub
    public static class EntityHitResult extends HitResult {
        public Entity getEntity() { return null; }
    }
    
    // Item Stack stub
    public static class ItemStack {
        public boolean isEmpty() { return true; }
        public String getName() { return ""; }
        public int getCount() { return 0; }
    }
    
    // Sound Event stub
    public static class SoundEvent {
        public String getId() { return ""; }
    }
    
    // Vertex Consumer stub
    public static class VertexConsumer {
        public VertexConsumer vertex(double x, double y, double z) { return this; }
        public VertexConsumer color(int r, int g, int b, int a) { return this; }
        public VertexConsumer texture(float u, float v) { return this; }
        public VertexConsumer normal(float x, float y, float z) { return this; }
        public void next() {}
    }
    
    // Particle Manager stub
    public static class ParticleManager {
        public void addParticle(ParticleEffect effect, double x, double y, double z, double vx, double vy, double vz) {}
    }
    
    // FabricLoader stub
    public static class FabricLoader {
        public static FabricLoader getInstance() { return new FabricLoader(); }
        public Object getConfigDir() { return null; }
        public boolean isModLoaded(String modId) { return false; }
    }
}