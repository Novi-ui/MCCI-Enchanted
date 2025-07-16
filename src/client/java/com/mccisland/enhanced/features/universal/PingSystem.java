package com.mccisland.enhanced.features.universal;

import com.mccisland.enhanced.config.PingSettings;
import com.mccisland.enhanced.features.Feature;
import com.mccisland.enhanced.util.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PingSystem implements Feature {
    private final PingSettings settings;
    private final MinecraftClient client;
    private final List<Ping> activePings;
    
    public PingSystem(PingSettings settings) {
        this.settings = settings;
        this.client = MinecraftClient.getInstance();
        this.activePings = new ArrayList<>();
    }
    
    @Override
    public void tick() {
        if (!isEnabled()) return;
        
        // Update existing pings
        Iterator<Ping> iterator = activePings.iterator();
        while (iterator.hasNext()) {
            Ping ping = iterator.next();
            ping.update();
            if (ping.isExpired()) {
                iterator.remove();
            }
        }
    }
    
    @Override
    public void renderWorld(DrawContext context, float tickDelta) {
        if (!isEnabled() || activePings.isEmpty()) return;
        
        for (Ping ping : activePings) {
            ping.render(context, tickDelta);
        }
    }
    
    public void createPing() {
        if (!isEnabled() || client.player == null) return;
        
        HitResult hitResult = client.crosshairTarget;
        if (hitResult == null) return;
        
        Ping ping = null;
        
        switch (hitResult.getType()) {
            case ENTITY -> {
                if (settings.enableEnemyPing) {
                    EntityHitResult entityHit = (EntityHitResult) hitResult;
                    Entity entity = entityHit.getEntity();
                    if (entity instanceof PlayerEntity) {
                        ping = new Ping(PingType.ENEMY, entity.getPos(), entity.getName().getString());
                    } else {
                        ping = new Ping(PingType.ITEM, entity.getPos(), entity.getName().getString());
                    }
                }
            }
            case BLOCK -> {
                if (settings.enableLocationPing) {
                    BlockHitResult blockHit = (BlockHitResult) hitResult;
                    BlockPos pos = blockHit.getBlockPos();
                    String blockName = client.world.getBlockState(pos).getBlock().getName().getString();
                    ping = new Ping(PingType.LOCATION, Vec3d.ofCenter(pos), blockName);
                }
            }
        }
        
        if (ping != null) {
            activePings.add(ping);
            
            if (settings.playSound) {
                client.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0.5f, 1.0f);
            }
            
            if (settings.showPingInChat) {
                // TODO: Send ping message to chat
            }
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
        return "Ping System";
    }
    
    @Override
    public String getDescription() {
        return "Allows pinging locations, enemies, and items for team coordination";
    }
    
    public enum PingType {
        ENEMY(0xFF0000, "Enemy"),
        ITEM(0x00FF00, "Item"),
        LOCATION(0x0000FF, "Location"),
        DANGER(0xFF8800, "Danger");
        
        private final int color;
        private final String displayName;
        
        PingType(int color, String displayName) {
            this.color = color;
            this.displayName = displayName;
        }
        
        public int getColor() {
            return color;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private class Ping {
        private final PingType type;
        private final Vec3d position;
        private final String targetName;
        private final long creationTime;
        private float scale = 1.0f;
        
        public Ping(PingType type, Vec3d position, String targetName) {
            this.type = type;
            this.position = position;
            this.targetName = targetName;
            this.creationTime = System.currentTimeMillis();
        }
        
        public void update() {
            long elapsed = System.currentTimeMillis() - creationTime;
            float progress = (float) elapsed / settings.pingDuration;
            
            // Animate scale
            if (progress < 0.2f) {
                scale = 1.0f + (progress / 0.2f) * 0.5f; // Scale up
            } else if (progress > 0.8f) {
                scale = 1.5f - ((progress - 0.8f) / 0.2f) * 0.5f; // Scale down
            } else {
                scale = 1.5f;
            }
        }
        
        public void render(DrawContext context, float tickDelta) {
            if (client.player == null) return;
            
            double distance = client.player.getPos().distanceTo(position);
            if (distance > settings.pingRange) return;
            
            float opacity = Math.max(0.2f, 1.0f - (float)(distance / settings.pingRange));
            int color = RenderUtils.applyOpacity(type.getColor(), opacity);
            
            // Render ping marker
            RenderUtils.renderPingMarker(context, position, scale * settings.pingScale, color);
            
            // Render distance if enabled
            if (settings.showDistance) {
                String distanceText = String.format("%.1fm", distance);
                RenderUtils.renderTextAtPosition(context, distanceText, position.add(0, 2, 0), 0.8f, 0xFFFFFF);
            }
            
            // Render target name
            if (targetName != null && !targetName.isEmpty()) {
                RenderUtils.renderTextAtPosition(context, targetName, position.add(0, 1.5, 0), 0.9f, color);
            }
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() - creationTime > settings.pingDuration;
        }
    }
}