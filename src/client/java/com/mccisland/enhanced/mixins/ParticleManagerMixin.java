package com.mccisland.enhanced.mixins;

import com.mccisland.enhanced.MCCIslandEnhancedClient;
import com.mccisland.enhanced.features.universal.ParticleOptimizer;
import com.mccisland.enhanced.compat.MinecraftCompat;
import com.mccisland.enhanced.util.MinecraftVersionUtil;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    
    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(CallbackInfo ci) {
        // Only apply optimization if the feature is supported in this version
        if (!MinecraftCompat.supportsFeature(MinecraftCompat.CompatFeature.PARTICLE_OPTIMIZATION)) {
            return;
        }
        
        try {
            MCCIslandEnhancedClient client = MCCIslandEnhancedClient.getInstance();
            if (client != null && client.getConfig().modEnabled) {
                ParticleOptimizer optimizer = client.getFeatureManager().getParticleOptimizer();
                if (optimizer != null && optimizer.isEnabled()) {
                    // Simple optimization - reduce particle count based on performance settings
                    if (Math.random() > 0.7) { // Keep 70% of particles
                        ci.cancel();
                    }
                }
            }
        } catch (Exception e) {
            // Fail silently to prevent crashes
        }
    }
}