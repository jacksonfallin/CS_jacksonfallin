import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Quake extends Animatable
{   
    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
    {
        super(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public PImage getCurrentImage()
    {
        return getImageList().get(getImageIndex());
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
            Create.createActivityAction(this, world, imageStore),
            this.actionPeriod);
        scheduler.scheduleEvent(this,
            Create.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT),
            this.getAnimationPeriod());
    }

    public static Activity createActivityAction(ActionEntity entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    public static Animation createAnimationAction(Animatable entity, int repeatCount)
    {
        return new Animation(entity, null, null, repeatCount);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
}