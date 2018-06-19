import java.util.List;
import processing.core.PImage;
import java.util.Random;
import java.util.Optional;
import processing.core.PImage;

public class Vein extends ActivityAbst
{
    private  static final Random rand = new Random();
    private static final String ORE_ID_PREFIX = "fire -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;

    public Vein(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(position());

        if (openPt.isPresent())
        {
            Ore ore = Point.createOre(ORE_ID_PREFIX + id(), openPt.get(), ORE_CORRUPT_MIN + rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
            imageStore.getImageList(VirtualWorld.ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, Point.createActivityAction(this, world, imageStore), this.getActionPeriod());
    }
}