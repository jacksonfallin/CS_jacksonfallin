import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Vein extends ActionEntity
{
    public static final String VEIN_KEY = "vein";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;

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

    public Vein(String id, Point position,
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
            Optional<Point> openPt = world.findOpenAround(getPosition());

            if (openPt.isPresent())
            {
                Ore ore = Create.createOre(ORE_ID_PREFIX + getid(),
                        openPt.get(), ORE_CORRUPT_MIN +
                                rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                        imageStore.getImageList(ORE_KEY));
                world.addEntity(ore);
                ore.scheduleActions(scheduler, world, imageStore);
            }

            scheduler.scheduleEvent(this,
                    Create.createActivityAction(this, world, imageStore),
                    this.actionPeriod());
        }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
            Create.createActivityAction(this, world, imageStore),
            this.actionPeriod);
    }
}
