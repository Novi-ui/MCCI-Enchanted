package net.minecraft.client.util;

/**
 * Stub class for InputUtil
 */
public class InputUtil {
    
    public static Key fromKeyCode(int keyCode, int scanCode) {
        return new Key(keyCode, scanCode);
    }
    
    public static class Key {
        private final int keyCode;
        private final int scanCode;
        
        public Key(int keyCode, int scanCode) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
        }
        
        public int getCode() {
            return keyCode;
        }
        
        public int getScanCode() {
            return scanCode;
        }
    }
}