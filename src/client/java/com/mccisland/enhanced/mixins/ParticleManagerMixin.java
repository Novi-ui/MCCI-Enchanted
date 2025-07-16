package com.mccisland.enhanced.mixins;

import com.mccisland.enhanced.MCCIslandEnhancedClient;
import com.mccisland.enhanced.features.universal.ParticleOptimizer;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfo ci) {
        MCCIslandEnhancedClient client = MCCIslandEnhancedClient.getInstance();
        if (client != null && client.getConfig().modEnabled) {
            ParticleOptimizer optimizer = client.getFeatureManager().getParticleOptimizer();
            if (optimizer != null && optimizer.isEnabled()) {
                Vec3d position = new Vec3d(x, y, z);
                if (!optimizer.shouldSpawnParticle(parameters, position)) {
                    ci.cancel();
                }
            }
        }
    }
}