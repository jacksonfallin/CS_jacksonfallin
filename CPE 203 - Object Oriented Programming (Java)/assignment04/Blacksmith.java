import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Blacksmith extends Entity
{
	public int animationPeriod;

   	public static final String SMITH_KEY = "blacksmith";
    public static final int SMITH_NUM_PROPERTIES = 4;
    public static final int SMITH_ID = 1;
    public static final int SMITH_COL = 2;
    public static final int SMITH_ROW = 3;

	public Blacksmith()
	{
		super();
	}

	public Blacksmith(String id, Point position,
        List<PImage> images)
	{
		super(id, position, images, 0, 0, 0, 0);
	}

    public boolean parseSmith(String [] properties, WorldModel world,
        ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = new Blacksmith(properties[SMITH_ID],
                pt, Entity.getImageList(imageStore, SMITH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    public boolean processLine(String line, WorldModel world,
        ImageStore imageStore)
    {
        /*String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            return parseSmith(properties, world, imageStore);
        }*/
        return true;
    }
}
