package com.mccisland.enhanced.mixins;

import com.mccisland.enhanced.MCCIslandEnhancedClient;
import com.mccisland.enhanced.compat.MinecraftCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.render.WorldRenderer")
public class WorldRendererMixin {
    
    @Inject(method = "render*", at = @At("HEAD"), remap = false)
    private void onRender(CallbackInfo ci) {
        // Only apply world rendering modifications if the feature is supported in this version
        if (!MinecraftCompat.supportsFeature(MinecraftCompat.CompatFeature.MODERN_RENDERING)) {
            return;
        }
        
        try {
            MCCIslandEnhancedClient client = MCCIslandEnhancedClient.getInstance();
            if (client != null && client.getConfig().modEnabled) {
                // Apply world rendering modifications here
                // This is where you would add your custom world rendering code
            }
        } catch (Exception e) {
            // Fail silently to prevent crashes
        }
    }
}