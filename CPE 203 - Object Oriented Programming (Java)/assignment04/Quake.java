import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Quake extends Entity
{
	public int animationPeriod;
    
    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

	public Quake()
	{
		super();
		this.animationPeriod = 100;
    }

    public Quake(String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
    {
        super(QUAKE_ID, position, images, 0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
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
        scheduler.scheduleEvent(this,
            Animation.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT),
            this.getAnimationPeriod());
    }

    public Entity createQuake(Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images,
            0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }
}