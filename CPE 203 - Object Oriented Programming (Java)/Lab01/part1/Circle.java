import java.lang.*;
import java.util.*;

class Circle
{
	private final Point point;
	private final double radius;

	public Circle(Point point, double radius)
	{
		this.point = point;
		this.radius = radius;
	}

	public Point getCenter() 
	{
		return this.point;
	} 
	
	public double getRadius()
	{
		return this.radius;
	}
}