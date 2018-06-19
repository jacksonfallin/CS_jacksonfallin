import java.lang.*;
import java.util.*;

class Bigger
{

	public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon)
	{
		double c = circle.perimeter();
		double r = rectangle.perimeter();
		double p = polygon.perimeter();
		double ans = 0;

		if (c < p && r < p)
		{
			return ans + p;
		}

		if (c < r && p < r)
		{
			return  ans + p;
		}

		if (r < c && p < c)
		{
			return ans + p;
		}

		return ans;
	}
}
