package net.minecraft.text;

/**
 * Stub class for Text
 */
public interface Text {
    String getString();
    
    static Text literal(String text) {
        return new SimpleText(text);
    }
    
    static Text of(String text) {
        return literal(text);
    }
    
    class SimpleText implements Text {
        private final String text;
        
        public SimpleText(String text) {
            this.text = text;
        }
        
        @Override
        public String getString() {
            return text;
        }
        
        @Override
        public String toString() {
            return text;
        }
    }
}