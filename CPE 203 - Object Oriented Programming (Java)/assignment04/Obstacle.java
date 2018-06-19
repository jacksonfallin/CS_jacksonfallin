import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Obstacle extends Entity
{
	public int animationPeriod;

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 4;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;

	public Obstacle()
	{
		super();
	}

    public Obstacle(String id, Point position, List<PImage> images)
    {
        super(id, position, images, 0, 0, 0, 0);
    }

    public boolean parseObstacle(String [] properties, WorldModel world,
        ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                Integer.parseInt(properties[OBSTACLE_COL]),
                Integer.parseInt(properties[OBSTACLE_ROW]));
            /*Entity entity = new Obstacle(properties[OBSTACLE_ID],
                pt, Entity.getImageList(imageStore, OBSTACLE_KEY));
            world.tryAddEntity(entity);*/
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    public boolean processLine(String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            return true;//this.parseObstacle(properties, imageStore);
        }
        return true;
    }
}
