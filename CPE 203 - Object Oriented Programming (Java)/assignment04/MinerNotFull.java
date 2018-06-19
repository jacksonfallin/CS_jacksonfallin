import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class MinerNotFull extends Entity
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

	public MinerNotFull()
	{
		super(); 
        this.animationPeriod = 6;
	}

    public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public MinerNotFull(String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
    {
        super(id, position, images,
            resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler,
        WorldModel world, ImageStore imageStore)
    {
            scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.actionPeriod);
            scheduler.scheduleEvent(this,
                Animation.createAnimationAction(this, 0), this.getAnimationPeriod());
    }

    public Entity createMinerNotFull(String id, int resourceLimit,
        Point position, int actionPeriod, int animationPeriod,
        List<PImage> images)
    {
        return new MinerNotFull(id, position, images,
            resourceLimit, 0, actionPeriod, animationPeriod);
    }
}
