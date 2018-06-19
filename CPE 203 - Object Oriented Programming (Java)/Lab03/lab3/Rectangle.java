import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.awt.*;
import java.lang.*;

class Rectangle implements Shape
{
	private Color color;
	private double width;
	private double height;
	private Point topLeft;

	public Rectangle(double width, double height, Point topLeft, Color color)
	{
		this.width = width;
		this.height = height;
		this.topLeft = topLeft;
		this.color = color;
	}

	public double getWidth()
	{
		return this.width;
	}

	public void setWidth(double w)
	{
		this.width = w;
	}

	public double getHeight()
	{
		return this.height;
	}

	public void setHeight(double h)
	{
		this.height = h;
	}

	public Point getTopLeft()
	{
		return this.topLeft;
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
		return (this.width * this.height);
	}

	@Override
	public double getPerimeter()
	{
		return ((2 * this.width) + (2 * this.height));
	}

	@Override
	public void translate(Point p)
	{
		topLeft.setLocation(topLeft.getX() + p.getX(), topLeft.getY() + p.getY());
	}

}