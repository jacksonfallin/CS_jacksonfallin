import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

final class Point
{
    public final int x;
    public final int y;

    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other)
    {
        return other instanceof Point &&
            ((Point)other).x == this.x &&
            ((Point)other).y == this.y;
    }

    public int hashCode()
    {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public boolean adjacent(Point p2)
    {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
            (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
    }

    public Entity createBlacksmith(String id, List<PImage> images)
    {
        return new Entity(EntityKind.BLACKSMITH, id, this, images,
            0, 0, 0, 0);
    }

    public Entity createMinerFull(String id, int resourceLimit,
        int actionPeriod, int animationPeriod,
        List<PImage> images)
    {
        return new Entity(EntityKind.MINER_FULL, id, this, images,
            resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public Entity createMinerNotFull(String id, int resourceLimit,
        int actionPeriod, int animationPeriod,
        List<PImage> images)
    {
        return new Entity(EntityKind.MINER_NOT_FULL, id, this, images,
            resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public Entity createObstacle(String id, List<PImage> images)
    {
        return new Entity(EntityKind.OBSTACLE, id, this, images,
            0, 0, 0, 0);
    }

    public Entity createOre(String id, int actionPeriod,
        List<PImage> images)
    {
        return new Entity(EntityKind.ORE, id, this, images, 0, 0,
            actionPeriod, 0);
    }

    public Entity createOreBlob(String id,
        int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.ORE_BLOB, id, this, images,
                0, 0, actionPeriod, animationPeriod);
    }

    public Entity createQuake(List<PImage> images)
    {
        return new Entity(EntityKind.QUAKE, QUAKE_ID, this, images,
            0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public Entity createVein(String id, int actionPeriod,
        List<PImage> images)
    {
        return new Entity(EntityKind.VEIN, id, this, images, 0, 0,
            actionPeriod, 0);
    }

    public int distanceSquared(Point p2)
    {
        int deltaX = this.x - p2.x;
        int deltaY = this.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }
}
