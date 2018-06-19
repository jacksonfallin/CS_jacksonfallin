import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.lang.*;

class WorkSpace 
{
	private List<Shape> instances;

	public WorkSpace()
	{
		this.instances = new ArrayList<Shape>();
	}

	public void add(Shape shape)
	{
		this.instances.add(shape);
	}

	public Shape get(int index)
	{
		return this.instances.get(index);
	}

	public int size()
	{
		return this.instances.size();
	}

	public List<Circle> getCircles()
	{
		List<Circle> list = new ArrayList<>();

		for (Shape i : this.instances)
		{
			if ( i instanceof Circle)
			{
				list.add((Circle)i);
			}
		}

		return list;
	} 

	public List<Rectangle> getRectangles()
	{

		List<Rectangle> list = new ArrayList<>();

		for (Shape i : this.instances)
		{
			if ( i instanceof Rectangle)
			{
				list.add((Rectangle)i);
			}
		}

		return list;
	}

	public List<Triangle> getTriangles()
	{
		List<Triangle> list = new ArrayList<>();

		for (Shape i : this.instances)
		{
			if ( i instanceof Triangle)
			{
				list.add((Triangle)i);
			}
		}

		return list;	
	}

	public List<ConvexPolygon> getConvexPolygon()
	{
		List<ConvexPolygon> list = new ArrayList<>();

		for (Shape i : this.instances)
		{
			if ( i instanceof ConvexPolygon)
			{
				list.add((ConvexPolygon)i);
			}
		}

		return list;	
	}

	public double getAreaOfAllShapes()
	{
		double area = 0;
		for (Shape num: instances )
		{
			area += num.getArea();
		}

		return area;
	}
	public double getPerimeterOfAllShapes()
	{
		double perim = 0;
		for (Shape num: instances)
		{
			perim += num.getPerimeter();
		}
		return perim;
	}

}