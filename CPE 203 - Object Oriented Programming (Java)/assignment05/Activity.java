import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Activity extends ActionAbstract
{
    public WorldModel world;
    public ImageStore imageStore;
    public ActionEntity entity;

    public Activity(ActionEntity entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        super(world, imageStore, repeatCount);
        this.entity = entity;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.executeActivity(world(), imageStore(), scheduler);
    }

    public static Activity createActivityAction(ActionEntity entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        return new Activity(entity, world, imageStore, repeatCount);
    }
}