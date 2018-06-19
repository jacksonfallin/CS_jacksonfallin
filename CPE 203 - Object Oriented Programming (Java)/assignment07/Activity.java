public class Activity extends Action 
{
    public ActivityAbst entity;


    public Activity(ActivityAbst entity, WorldModel world, ImageStore imageStore, int repeatCount) 
    {
        super(world, imageStore, repeatCount);
        this.entity = entity;
        this.world = world;
      	this.imageStore = imageStore;
    }

    /*public static Activity createActivityAction(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
    	return new Activity(entity, world, imageStore, repeatCount);
    }*/

    public void executeAction(EventScheduler scheduler) 
    {
        entity.executeActivity(getWorld(), getImageStore(), scheduler);
    }

    public WorldModel getWorld() 
	{
		return world;
	}

	public ImageStore getImageStore() 
	{
		return imageStore;
	}
}

