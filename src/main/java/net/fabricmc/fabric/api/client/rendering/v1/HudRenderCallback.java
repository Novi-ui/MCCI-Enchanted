package net.fabricmc.fabric.api.client.rendering.v1;

import net.minecraft.client.gui.DrawContext;

/**
 * Stub class for HudRenderCallback
 */
@FunctionalInterface
public interface HudRenderCallback {
    void onHudRender(DrawContext drawContext, float tickDelta);
    
    HudRenderCallback.Event EVENT = new HudRenderCallback.Event();
    
    class Event {
        public void register(HudRenderCallback callback) {
            // Stub implementation
        }
    }
}