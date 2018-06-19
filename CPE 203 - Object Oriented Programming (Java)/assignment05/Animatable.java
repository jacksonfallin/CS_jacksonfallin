import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public abstract class Animatable extends ActionEntity
{
    public Animatable(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) 
    {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
        this.actionPeriod = actionPeriod;
        this.position = position;
        this.images = images;
        this.id = id;
    }

    public int animationPeriod()
    {
        return animationPeriod;
    }

    public void nextImage()
    {
        setImageIndex((getImageIndex() + 1) % getImageList().size());
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this, createAnimationAction(this, 0), animationPeriod);
    }

    public static Animation createAnimationAction(Animatable entity, int repeatCount)
    {
        return new Animation(entity, null, null, repeatCount);
    }
}