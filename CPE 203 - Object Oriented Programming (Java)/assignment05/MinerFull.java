import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class MinerFull extends Shift
{
   	public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    public MinerFull(String id, Point position,
        List<PImage> images, int resourceLimit,
        int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
        this.animationPeriod = 6;
    }

	public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public boolean moveToShift(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(getPosition(), target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public void scheduleActions(EventScheduler scheduler,
        WorldModel world, ImageStore imageStore)
    {
            scheduler.scheduleEvent(this,
                createActivityAction(this, world, imageStore),
                this.actionPeriod);
            scheduler.scheduleEvent(this, createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(getPosition(),
                Blacksmith.class);

        if (fullTarget.isPresent() &&
                moveToShift(world, fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    createActivityAction(this, world, imageStore),
                    actionPeriod());
        }
    }

    public static Activity createActivityAction(ActionEntity entity, WorldModel world,
        ImageStore imageStore, int repeatCount)
    {       
        return new Activity(entity, world, imageStore, repeatCount);
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = createMinerNotFull(id(), resourceLimit(),
            getPosition(), actionPeriod(), animationPeriod(), getImageList());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    public static MinerNotFull createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MinerNotFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }
}
