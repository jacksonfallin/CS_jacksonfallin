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

}