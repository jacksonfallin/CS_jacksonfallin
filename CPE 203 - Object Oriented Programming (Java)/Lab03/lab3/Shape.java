import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.awt.*;
import java.lang.*;

public interface Shape
{
	public Color getColor();
	public void setColor(Color c);
	public double getArea();
	public double getPerimeter();
	public void translate(Point p);
}