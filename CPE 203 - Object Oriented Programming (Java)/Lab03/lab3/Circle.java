import java.awt.Color;
import java.awt.Point;
import java.awt.*;
import java.lang.*;
import java.util.*;

public class Circle implements Shape
{
	private Color color;
	private double radius;
	private Point center;

	public Circle(double radius, Point Center, Color color)
	{
		this.radius = radius;
		this.center = center;
		this.color = color;
	}

	public double getRadius()
	{
		return this.radius;
	}

	public void setRadius(double rad)
	{
 		this.radius = rad;
	}

	public Point getCenter()
	{
		return this.center;
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
		return (Math.PI * Math.pow(this.radius, 2));
	}

	@Override
	public double getPerimeter()
	{
		return (2 * Math.PI * this.radius);
	}

	@Override
	public void translate(Point p)
	{
		center.setLocation(center.getX() + p.getX(), center.getY() + p.getY());
	}

}