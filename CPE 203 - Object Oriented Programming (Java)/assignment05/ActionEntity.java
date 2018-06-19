import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

import java.util.List;

public abstract class ActionEntity extends Entity
{
    public ActionEntity(String id, Point position, List<PImage> images, int actionPeriod) 
    {
        super(id, position, images, 0, 0, 0, 0);
        this.actionPeriod = actionPeriod;
        this.position = position;
        this.images = images;
        this.id = id;
    }

    public int actionPeriod()
    {
        return actionPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, createActivityAction(this, world, imageStore), actionPeriod);
    }

    public static Activity createActivityAction(ActionEntity entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}