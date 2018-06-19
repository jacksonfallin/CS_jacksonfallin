import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class OreBlob extends Shift
{
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

    public static final String QUAKE_KEY = "quake";

    public static final Random rand = new Random();

    public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;

    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

	public OreBlob(String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
	{
		super(id, position, images, 0, 0, actionPeriod, 0);
	}

	public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public boolean moveToShift(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), target.getPosition()))
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

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
            scheduler.scheduleEvent(this,
            Create.createActivityAction(this, world, imageStore),
            this.actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget;
        blobTarget = world.findNearest(getPosition(), Vein.class);
        long nextPeriod = actionPeriod();

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveToShift(world, blobTarget.get(), scheduler))
            {
                Quake quake = Create.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += actionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
            Create.createActivityAction(this, world, imageStore),
            nextPeriod);
    }
}