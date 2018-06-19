import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

public class Create 
{
    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

	public static MinerNotFull createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MinerNotFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static Obstacle createObstacle(String id, Point position, List<PImage> images)
    {
        return new Obstacle(id, position, images);
    }

    public static Ore createOre(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Ore(id, position, images, 0, 0, actionPeriod, 0);
    }

    public static Blacksmith createBlacksmith(String id, Point position, List<PImage> images)
    {
        return new Blacksmith(id, position, images);
    }

    public static Vein createVein(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Vein(id, position, images, 0, 0, actionPeriod, 0);
    }

    public static Animation createAnimationAction(Animatable entity, int repeatCount)
    {
        return new Animation(entity, null, null, repeatCount);
    }

    public static Activity createActivityAction(ActionEntity entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    public static Quake createQuake(Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images, 0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }
}