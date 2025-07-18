package net.minecraft.client.render.entity;

/**
 * Stub class for EntityRenderDispatcher
 */
public class EntityRenderDispatcher {
    
    public Object getRenderer(Object entity) {
        return new EntityRenderer();
    }
    
    public void render(Object entity, double x, double y, double z, float yaw, float tickDelta, Object matrixStack, Object vertexConsumerProvider, int light) {
        // Stub implementation
    }
}