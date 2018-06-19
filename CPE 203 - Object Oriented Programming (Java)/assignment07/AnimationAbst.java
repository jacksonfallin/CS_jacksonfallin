import processing.core.PImage;

import java.util.List;

public abstract class AnimationAbst extends ActivityAbst
{

    private int animationPeriod;
    private int repeatCount;
    private int imageIndex;

    public AnimationAbst(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) 
    {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod()
    {
        return animationPeriod;
    }

    //@Override
    public void nextImage()
    {
        setImageIndex((imageIndex() + 1) % images().size());
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this, Point.createAnimationAction(this, 0), animationPeriod);
    }

}