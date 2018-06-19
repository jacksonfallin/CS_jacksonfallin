import java.lang.*;
import java.util.*;

class Polygon
{
	private final List<Point> points;

	public Polygon(List<Point> points)
	{
		this.points = points;
	}

	public List<Point> getPoints() 
	{
		return this.points;
	} 

	public double perimeter()
	{
		double perim = 0;
		Point nextPoint;

		for (int i = 0; i < this.points.size(); i++)
		{
			Point startPoint = this.points.get(i);

			if (i < this.points.size() - 1)
			{
				nextPoint = this.points.get(i + 1);
			}
			else
			{
				nextPoint = this.points.get(0);
			}

			double distanceFormula = Math.pow((Math.pow(nextPoint.getX() - startPoint.getX(), 2)) + (Math.pow(nextPoint.getY() - startPoint.getY(), 2)), 1/2);
			perim += distanceFormula;
		}
	
		return perim;
	}

}