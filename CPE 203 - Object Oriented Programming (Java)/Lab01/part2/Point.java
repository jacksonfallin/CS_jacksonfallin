import java.lang.*;

class Point
{
	private final double x;
	private final double y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}

	public double getRadius()
	{
		double rad = Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
		return rad;
	}

	public double getAngle()
	{
		double angle = Math.atan2(this.getY(),this.getX());
		return angle; 

	}

	public Point rotate90()
	{
	//grader: what happens if this point is in quadrant 1?	
		return new Point ( -x, y); 
	}
}
