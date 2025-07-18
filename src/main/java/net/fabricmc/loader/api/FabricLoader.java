package net.fabricmc.loader.api;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Stub class for FabricLoader
 */
public class FabricLoader {
    private static final FabricLoader INSTANCE = new FabricLoader();
    
    public static FabricLoader getInstance() {
        return INSTANCE;
    }
    
    public Path getConfigDir() {
        return Paths.get("config");
    }
    
    public Path getGameDir() {
        return Paths.get(".");
    }
    
    public boolean isModLoaded(String modId) {
        return false;
    }
}