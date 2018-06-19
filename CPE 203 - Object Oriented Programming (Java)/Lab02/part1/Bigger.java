import java.lang.*;
import java.util.*;

class Bigger
{

	public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon)
	{
		double c = Util.perimeter(circle);
		double r = Util.perimeter(rectangle);
		double p = Util.perimeter(polygon);
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
