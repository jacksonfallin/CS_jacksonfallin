import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.awt.*;
import java.lang.*;

class ConvexPolygon implements Shape
{
	private Color color;
	private Point[] vertex;

	public ConvexPolygon(Point[] vertex, Color color)
	{
		this.vertex = vertex;
		this.color = color;
	}

	public Point getvertex(int i)
	{
		return this.vertex[i];
	}

	@Override
	public Color getColor()
	{
		return this.color;
	}

	@Override
	public void setColor(Color c)
	{
		this.color = c;
	}

	@Override
	public double getArea()
	{
		double area = 0;
		double grabX = this.vertex[0].getX();
		double grabY = this.vertex[0].getY();
		area += (this.vertex[this.vertex.length -1].getX() + grabX) * (this.vertex[this.vertex.length -1].getY() - grabY);

		for ( Point nextPoint : this.vertex)
		{
			area += (nextPoint.getX() + grabX * (nextPoint.getY() - grabY));
			grabX = nextPoint.getX();
			grabY = nextPoint.getY();
		}
		return (area * 0.5); 
	}

	@Override
	public double getPerimeter()
	{
		double perim = 0;
		double grabX = this.vertex[0].getX();
		double grabY = this.vertex[0].getY();
		perim += Math.sqrt(Math.pow((this.vertex[this.vertex.length - 1].getX() - grabX), 2) + Math.pow((this.vertex[this.vertex.length - 1].getY() - grabY), 2));

		for (Point nextPoint : this.vertex)
		{
			perim += Math.sqrt(Math.pow((nextPoint.getX() - grabX), 2) + Math.pow((nextPoint.getY() - grabY), 2));
			grabX = nextPoint.getX();
			grabY = nextPoint.getY();
		}
		return perim;
	}

	@Override
	public void translate(Point p)
	{
		for (int i = 0; i < vertex.length - 1; i++)
		{
			vertex[i].setLocation(vertex[i].getX() + p.getX(), vertex[i].getY() + p.getY());
		}
	}

}