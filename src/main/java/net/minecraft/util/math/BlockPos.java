package net.minecraft.util.math;

/**
 * Stub class for BlockPos
 */
public class BlockPos {
    public final int x;
    public final int y;
    public final int z;
    
    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public BlockPos(double x, double y, double z) {
        this((int) x, (int) y, (int) z);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getZ() {
        return z;
    }
}