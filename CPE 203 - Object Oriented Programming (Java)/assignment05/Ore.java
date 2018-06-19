import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Ore extends ActionEntity
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
    public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;

    private final Random rand = new Random();

	public Ore(String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
	{
		super(id, position, images, actionPeriod);
        this.animationPeriod = 4;
	}

	public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = getPosition(); 

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = createOreBlob(getid() + BLOB_ID_SUFFIX,
                pos, actionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN +
                        rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
            scheduler.scheduleEvent(this,
                createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    public static OreBlob createOreBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new OreBlob(id, position, images, 0, 0, actionPeriod, animationPeriod);
    }

    public static Activity createActivityAction(ActionEntity entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        return new Activity(entity, world, imageStore, repeatCount);
    }
}