import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Animation extends ActionAbstract
{
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;
    public Animatable entity;

    public Animation(Animatable entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        super(world, imageStore, repeatCount);
        this.entity = entity;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage() ;

        if (repeatCount() != 1)
        {
            scheduler.scheduleEvent(entity,
            Create.createAnimationAction(entity, Math.max(repeatCount() - 1, 0)),
            entity.animationPeriod());
        }
    }
}
