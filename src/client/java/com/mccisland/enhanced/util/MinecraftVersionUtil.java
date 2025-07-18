package com.mccisland.enhanced.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for handling Minecraft version compatibility
 */
public class MinecraftVersionUtil {
    
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)(?:\\.(\\d+))?");
    private static MinecraftVersion currentVersion;
    
    public enum MinecraftVersion {
        MC_1_19_4(1, 19, 4),
        MC_1_20_0(1, 20, 0),
        MC_1_20_1(1, 20, 1),
        MC_1_20_2(1, 20, 2),
        MC_1_20_3(1, 20, 3),
        MC_1_20_4(1, 20, 4),
        MC_1_20_5(1, 20, 5),
        MC_1_20_6(1, 20, 6),
        MC_1_21_0(1, 21, 0),
        MC_1_21_1(1, 21, 1),
        MC_1_21_2(1, 21, 2),
        MC_1_21_3(1, 21, 3),
        MC_1_21_4(1, 21, 4),
        MC_1_21_5(1, 21, 5),
        MC_1_21_6(1, 21, 6),
        MC_1_21_7(1, 21, 7),
        UNKNOWN(0, 0, 0);
        
        private final int major;
        private final int minor;
        private final int patch;
        
        MinecraftVersion(int major, int minor, int patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }
        
        public int getMajor() { return major; }
        public int getMinor() { return minor; }
        public int getPatch() { return patch; }
        
        public boolean isAtLeast(MinecraftVersion version) {
            if (this.major > version.major) return true;
            if (this.major < version.major) return false;
            if (this.minor > version.minor) return true;
            if (this.minor < version.minor) return false;
            return this.patch >= version.patch;
        }
        
        public boolean isAtMost(MinecraftVersion version) {
            if (this.major < version.major) return true;
            if (this.major > version.major) return false;
            if (this.minor < version.minor) return true;
            if (this.minor > version.minor) return false;
            return this.patch <= version.patch;
        }
        
        public boolean isBetween(MinecraftVersion min, MinecraftVersion max) {
            return this.isAtLeast(min) && this.isAtMost(max);
        }
        
        @Override
        public String toString() {
            return major + "." + minor + "." + patch;
        }
    }
    
    /**
     * Detects the current Minecraft version
     * @return The detected MinecraftVersion
     */
    public static MinecraftVersion getCurrentVersion() {
        if (currentVersion == null) {
            currentVersion = detectVersion();
        }
        return currentVersion;
    }
    
    /**
     * Detects the Minecraft version from available information
     * @return The detected MinecraftVersion
     */
    private static MinecraftVersion detectVersion() {
        try {
            // Try to get version from system properties or environment
            String version = System.getProperty("minecraft.version");
            if (version == null) {
                version = System.getenv("MINECRAFT_VERSION");
            }
            
            // If still null, try to detect from available classes or methods
            if (version == null) {
                version = detectFromClasses();
            }
            
            // Default to a reasonable version if detection fails
            if (version == null) {
                version = "1.21.1";
            }
            
            return parseVersion(version);
        } catch (Exception e) {
            // If all else fails, return a default version
            return MinecraftVersion.MC_1_21_1;
        }
    }
    
    /**
     * Attempts to detect version from available classes
     * @return Version string or null
     */
    private static String detectFromClasses() {
        // This is a simplified detection - in a real implementation,
        // you would check for specific classes or methods that exist
        // in different versions
        return "1.21.1"; // Default fallback
    }
    
    /**
     * Parses a version string into a MinecraftVersion enum
     * @param versionString The version string to parse
     * @return The corresponding MinecraftVersion
     */
    private static MinecraftVersion parseVersion(String versionString) {
        Matcher matcher = VERSION_PATTERN.matcher(versionString);
        if (!matcher.find()) {
            return MinecraftVersion.UNKNOWN;
        }
        
        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));
        int patch = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
        
        // Find matching version
        for (MinecraftVersion version : MinecraftVersion.values()) {
            if (version.major == major && version.minor == minor && version.patch == patch) {
                return version;
            }
        }
        
        return MinecraftVersion.UNKNOWN;
    }
    
    /**
     * Checks if the current version is supported
     * @return true if supported, false otherwise
     */
    public static boolean isSupported() {
        MinecraftVersion current = getCurrentVersion();
        return current.isBetween(MinecraftVersion.MC_1_19_4, MinecraftVersion.MC_1_21_7);
    }
    
    /**
     * Checks if a feature is available in the current version
     * @param minimumVersion The minimum version required
     * @return true if available, false otherwise
     */
    public static boolean isFeatureAvailable(MinecraftVersion minimumVersion) {
        return getCurrentVersion().isAtLeast(minimumVersion);
    }
    
    /**
     * Checks if a feature was deprecated in the current version
     * @param deprecatedVersion The version where the feature was deprecated
     * @return true if deprecated, false otherwise
     */
    public static boolean isFeatureDeprecated(MinecraftVersion deprecatedVersion) {
        return getCurrentVersion().isAtLeast(deprecatedVersion);
    }
}