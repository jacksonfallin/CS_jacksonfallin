import processing.core.PImage;
import java.util.List;

public abstract class ActivityAbst extends Entity 
{

    private int actionPeriod;

    public ActivityAbst(String id, Point position, List<PImage> images, int actionPeriod) 
    {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod()
    {
        return actionPeriod;
    }
    
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Point.createActivityAction(this, world, imageStore), actionPeriod);
    }
}