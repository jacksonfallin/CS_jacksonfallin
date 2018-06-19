import java.lang.*;
import java.util.*;

class Rectangle
{
	private final Point topLeft;
	private final Point botRight;

	public Rectangle(Point topLeft, Point botRight)
	{
		this.topLeft = topLeft;
		this.botRight = botRight;
	}

	public Point getTopLeft() 
	{
		return this.topLeft;
	} 
	
	public Point getBottomRight()
	{
		return this.botRight;
	}

	public double perimeter()
	{
		Point TL = this.topLeft;
		Point BR = this.botRight;

		double perim1 = (2*Math.abs(TL.getX() - BR.getX()));
		double perim2 = (2*Math.abs(TL.getY() - BR.getY()));

		return perim1 + perim2; 
	}
}