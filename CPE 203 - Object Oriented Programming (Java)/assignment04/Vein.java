import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Vein extends Entity
{
	public int animationPeriod;

    public static final String VEIN_KEY = "vein";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;

	public Vein()
	{
		super();
		this.animationPeriod = 4;
	}

    public Vein(String id, Point position,
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

    public Entity createVein(String id, Point position, int actionPeriod,
        List<PImage> images)
    {
        return new Vein(id, position, images, 0, 0, actionPeriod, 0);
    }

    public boolean parseVein(String [] properties, WorldModel world,
        ImageStore imageStore)
    {
        if (properties.length == VEIN_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                Integer.parseInt(properties[VEIN_ROW]));
            /*Entity entity = new Vein(properties[VEIN_ID],
                pt,
                Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                Entity.getImageList(imageStore, VEIN_KEY));
            world.tryAddEntity(entity);*/
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }

    public boolean processLine(String line, WorldModel world,
        ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            return parseVein(properties, world, imageStore);
        }
        return true;
    }
}
