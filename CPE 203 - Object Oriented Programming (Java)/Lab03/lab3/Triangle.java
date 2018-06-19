import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.awt.*;
import java.lang.*;

class Triangle implements Shape
{
	private Color color;
	private Point vertA;
	private Point vertB;
	private Point vertC;

	public Triangle(Point vertA, Point vertB, Point vertC, Color color)
	{
		this.vertA = vertA;
		this.vertB = vertB;
		this.vertC = vertC;
		this.color = color;
	}

	public Point getVertexA()
	{
		return this.vertA;
	}

	public Point getVertexB()
	{
		return this.vertB;
	}

	public Point getVertexC()
	{
		return this.vertC;
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
		double distance1 = Math.pow((Math.pow(vertA.getX() - vertB.getX(), 2)) + (Math.pow(vertA.getY() - vertB.getY(), 2)), 0.5);
		double distance2 = Math.pow((Math.pow(vertA.getX() - vertC.getX(), 2)) + (Math.pow(vertA.getY() - vertC.getY(), 2)), 0.5);
		double distance3 = Math.pow((Math.pow(vertB.getX() - vertC.getX(), 2)) + (Math.pow(vertB.getY() - vertC.getY(), 2)), 0.5);
		
		double s = (0.5 * (distance3 + distance2 + distance1));

		return (Math.sqrt(s * (s - distance1) * (s - distance2) * (s - distance3)));
	}

	@Override
	public double getPerimeter()
	{
		double distance1 = Math.pow((Math.pow(vertA.getX() - vertB.getX(), 2)) + (Math.pow(vertA.getY() - vertB.getY(), 2)), 0.5);
		double distance2 = Math.pow((Math.pow(vertA.getX() - vertC.getX(), 2)) + (Math.pow(vertA.getY() - vertC.getY(), 2)), 0.5);
		double distance3 = Math.pow((Math.pow(vertB.getX() - vertC.getX(), 2)) + (Math.pow(vertB.getY() - vertC.getY(), 2)), 0.5);

		return (distance3 + distance2 + distance1);
	}

	@Override
	public void translate(Point p)
	{
		vertA.setLocation(vertA.getX() + p.getX(), vertA.getY() + p.getY());
		vertB.setLocation(vertB.getX() + p.getX(), vertB.getY() + p.getY());
		vertC.setLocation(vertC.getX() + p.getX(), vertC.getY() + p.getY());
	}

}
