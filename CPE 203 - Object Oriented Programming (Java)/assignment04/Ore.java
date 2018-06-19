import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Ore extends Entity
{
	public int animationPeriod;

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;
    public static final int ORE_REACH = 1;
    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;

	public Ore()
	{
		super();
		this.animationPeriod = 4;
	}

	public Ore(String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
	{
		super(id, position, images, 0, 0, actionPeriod, 0);
	}

	public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler,
        WorldModel world, ImageStore imageStore)
    {
            scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    public Entity createOre(String id, Point position, int actionPeriod,
        List<PImage> images)
    {
        return new Ore(id, position, images, 0, 0, actionPeriod, 0);
    }

    public boolean parseOre(String [] properties, WorldModel world,
        ImageStore imageStore)
    {
        if (properties.length == ORE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                Integer.parseInt(properties[ORE_ROW]));
            /*Entity entity =  new Ore(properties[ORE_ID],
                pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                Entity.getImageList(imageStore, ORE_KEY));
            world.tryAddEntity(entity);*/
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }

    public boolean processLine(String line, WorldModel world,
        ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {

            return parseOre(properties, world, imageStore);
        }
        return true;
    }
}