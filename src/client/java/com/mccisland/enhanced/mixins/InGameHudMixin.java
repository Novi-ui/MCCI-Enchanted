package com.mccisland.enhanced.mixins;

import com.mccisland.enhanced.MCCIslandEnhancedClient;
import com.mccisland.enhanced.compat.MinecraftCompat;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(CallbackInfo ci) {
        // Only apply HUD modifications if the feature is supported in this version
        if (!MinecraftCompat.supportsFeature(MinecraftCompat.CompatFeature.ADVANCED_HUD)) {
            return;
        }
        
        try {
            MCCIslandEnhancedClient client = MCCIslandEnhancedClient.getInstance();
            if (client != null && client.getConfig().modEnabled) {
                // Render custom HUD elements here
                // This is where you would add your custom rendering code
            }
        } catch (Exception e) {
            // Fail silently to prevent crashes
        }
    }
}