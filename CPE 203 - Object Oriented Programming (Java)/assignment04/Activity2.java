import processing.core.PImage;
import java.util.List;

public abstract class Activity2 extends Entity implements ActionEntity
{
	private int actionPeriod;

	public Activity2(String id, Point position,
							List<PImage> images, int resourceLimit, 
							int resourceCount, int actionPeriod) 
	{
		this.actionPeriod = actionPeriod;
	}

	public int getactionPeriod() { return actionPeriod; }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
         scheduler.scheduleEvent(this,
            Activity.createActivityAction(this, world, imageStore),
            this.getactionPeriod());	
	}
}