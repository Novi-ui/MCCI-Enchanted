package net.minecraft.util.math;

/**
 * Stub class for Vec3d
 */
public class Vec3d {
    public final double x;
    public final double y;
    public final double z;
    
    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getZ() {
        return z;
    }
    
    public Vec3d add(Vec3d vec) {
        return new Vec3d(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }
    
    public Vec3d subtract(Vec3d vec) {
        return new Vec3d(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }
    
    public double distanceTo(Vec3d vec) {
        double dx = this.x - vec.x;
        double dy = this.y - vec.y;
        double dz = this.z - vec.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }
}