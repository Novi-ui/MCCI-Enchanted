package net.fabricmc.fabric.api.client.event.lifecycle.v1;

/**
 * Stub class for ClientTickEvents
 */
public class ClientTickEvents {
    public static final EndTick END_CLIENT_TICK = new EndTick();
    
    public static class EndTick {
        public void register(EndTickCallback callback) {
            // Stub implementation
        }
    }
    
    @FunctionalInterface
    public interface EndTickCallback {
        void onEndTick(Object client);
    }
}