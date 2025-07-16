package com.mccisland.enhanced.input;

import com.mccisland.enhanced.MCCIslandEnhancedClient;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyBinding TOGGLE_MOD;
    public static KeyBinding PING_KEY;
    public static KeyBinding QUICK_CONFIG;
    public static KeyBinding TOGGLE_MODULES;
    public static KeyBinding EMERGENCY_DISABLE;
    public static KeyBinding TOGGLE_PARTICLES;
    
    public static void register() {
        TOGGLE_MOD = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcc-island-enhanced.toggle_mod",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.mcc-island-enhanced.general"
        ));
        
        PING_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcc-island-enhanced.ping",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
                "category.mcc-island-enhanced.general"
        ));
        
        QUICK_CONFIG = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcc-island-enhanced.quick_config",
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                "category.mcc-island-enhanced.general"
        ));
        
        TOGGLE_MODULES = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcc-island-enhanced.toggle_modules",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                "category.mcc-island-enhanced.general"
        ));
        
        EMERGENCY_DISABLE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcc-island-enhanced.emergency_disable",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                "category.mcc-island-enhanced.general"
        ));
        
        TOGGLE_PARTICLES = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcc-island-enhanced.toggle_particles",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.mcc-island-enhanced.general"
        ));
    }
    
    public static void handleInput() {
        MCCIslandEnhancedClient client = MCCIslandEnhancedClient.getInstance();
        
        while (TOGGLE_MOD.wasPressed()) {
            client.getConfig().modEnabled = !client.getConfig().modEnabled;
            client.getConfig().save();
        }
        
        while (PING_KEY.wasPressed()) {
            if (client.getConfig().pingSettings.enabled && client.getConfig().modEnabled) {
                client.getFeatureManager().getPingSystem().createPing();
            }
        }
        
        while (QUICK_CONFIG.wasPressed()) {
            // TODO: Open quick config screen
        }
        
        while (TOGGLE_MODULES.wasPressed()) {
            client.getFeatureManager().toggleAllModules();
        }
        
        while (EMERGENCY_DISABLE.wasPressed()) {
            client.getConfig().modEnabled = false;
            client.getConfig().save();
        }
        
        while (TOGGLE_PARTICLES.wasPressed()) {
            client.getConfig().particleSettings.enabled = !client.getConfig().particleSettings.enabled;
            client.getConfig().save();
        }
    }
}