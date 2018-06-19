import processing.core.PImage;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public abstract class Shift extends Animatable 
{
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;
    public static final Random rand = new Random();

    public Shift(String id, Point position, List<PImage> images, int resourceLimit, 
        int resourceCount, int actionPeriod, int animationPeriod) 
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.animationPeriod = animationPeriod;
        this.actionPeriod = actionPeriod;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.position = position;
        this.images = images;
        this.id = id;
    }

    public abstract boolean moveToShift(WorldModel world, Entity target, EventScheduler scheduler);

    public int resourceCount()
    {
        return resourceCount;
    }
    public void setResourceCount(int resourceCount)
    {
        this.resourceCount = resourceCount;
    }

    public int resourceLimit()
    {
        return resourceLimit;
    }

    public String id()
    {
        return id;
    }
}
