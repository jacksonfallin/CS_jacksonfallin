import java.lang.*;

class Util
{
	public static double perimeter(Circle circle)
	{
		return 2*Math.PI*circle.getRadius();
	}

	public static double perimeter(Rectangle rectangle)
	{
		Point TL = rectangle.getTopLeft();
		Point BR = rectangle.getBottomRight();

		double perim1 = (2*Math.abs(TL.getX() - BR.getX()));
		double perim2 = (2*Math.abs(TL.getY() - BR.getY()));

		return perim1 + perim2; 
	}

	public static double perimeter(Polygon polygon)
	{
		double perim = 0;
		Point nextPoint;

		for (int i = 0; i < polygon.getPoints().size(); i++)
		{
			Point startPoint = polygon.getPoints().get(i);

			if (i < polygon.getPoints().size() - 1)
			{
				nextPoint = polygon.getPoints().get(i + 1);
			}
			else
			{
				nextPoint = polygon.getPoints().get(0);
			}

			double distanceFormula = Math.pow((Math.pow(nextPoint.getX() - startPoint.getX(), 2)) + (Math.pow(nextPoint.getY() - startPoint.getY(), 2)), 1/2);
			perim += distanceFormula;
		}
	
		return perim;
	}
}
