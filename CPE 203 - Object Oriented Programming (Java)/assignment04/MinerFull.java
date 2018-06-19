import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class MinerFull extends Entity
{
	public int animationPeriod;

   	public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

	public MinerFull()
	{
		super();
		this.animationPeriod = 6;
	}

    public MinerFull(String id, Point position,
        List<PImage> images, int resourceLimit,
        int actionPeriod, int animationPeriod)
    {
        super(id, position, images,
            resourceLimit, resourceLimit, actionPeriod, animationPeriod);
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
            scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }

    public boolean parseMiner(String [] properties, WorldModel world,
        ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                Integer.parseInt(properties[MINER_ROW]));
            /*Entity entity = new MinerNotFull(properties[MINER_ID],
                Integer.parseInt(properties[MINER_LIMIT]),
                pt,
                Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                Entity.getImageList(imageStore, MINER_KEY));
            world.tryAddEntity(entity);*/
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    public boolean processLine(String line, WorldModel world,
        ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            return parseMiner(properties, world, imageStore);
        }
        return true;
    }

    public void executeMinerFullActivity(WorldModel world,
        ImageStore imageStore, EventScheduler scheduler)
    {
        return;
        //Optional<Entity> fullTarget = findNearest(world, this.position);

        /*if (fullTarget.isPresent() &&
            moveToFull(world, fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(entity,
                Activity.createActivityAction(entity, world, imageStore),
                entity.actionPeriod);
        }*/
    }

    public static void transformFull(Entity entity, WorldModel world,
        EventScheduler scheduler, ImageStore imageStore)
    {
        return;
        //Entity miner = createMinerNotFull(entity.id, entity.resourceLimit,
            //entity.position, entity.actionPeriod, entity.animationPeriod,
            //entity.images);

        //removeEntity(world, entity);
        //unscheduleAllEvents(scheduler, entity);

        //addEntity(world, miner);
        //scheduleActions(miner, scheduler, world, imageStore);
    }
}
