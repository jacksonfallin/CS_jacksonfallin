public class Point 
{

    public final double x;
    public final double y;
    public final double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() 
    {
        return "Point(" + x + ", " + y + ", " + z + ")";
    }    

    public double getX() 
    {
        return this.x;
    }

    public double getY() 
    {
        return this.y;
    }

    public double getZ() 
    {
        return this.z;
    }
}
