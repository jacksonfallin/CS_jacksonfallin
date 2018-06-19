import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class MinerNotFull extends Shift
{
   	public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    public MinerNotFull(String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
        this.animationPeriod = 6;
    }

    public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler,
        WorldModel world, ImageStore imageStore)
    {
            scheduler.scheduleEvent(this,
                createActivityAction(this, world, imageStore),
                this.actionPeriod);
            scheduler.scheduleEvent(this,
                createAnimationAction(this, 0), this.getAnimationPeriod());
    }

    public static Animation createAnimationAction(Animatable entity, int repeatCount)
    {
        return new Animation(entity, null, null, repeatCount);
    }

    public boolean moveToShift(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), target.getPosition()))
        {
            this.setResourceCount(resourceCount() + 1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

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

    public Entity createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MinerNotFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static MinerFull createMinerFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MinerFull(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(getPosition(),
                Ore.class);

        if (!notFullTarget.isPresent() ||
                !moveToShift(world, notFullTarget.get(), scheduler) ||
                !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    createActivityAction(this, world, imageStore), actionPeriod());
        }
    }

    public static Activity createActivityAction(ActionEntity entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount() >= resourceLimit())
        {
            MinerFull miner = createMinerFull(id(), resourceLimit(),
                    getPosition(), actionPeriod(), animationPeriod(), getImageList());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
}
