public abstract class Action
{
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public Action(WorldModel world, ImageStore imageStore, int repeatCount)
    {
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
        this.world = world;
    }

    public abstract void executeAction(EventScheduler scheduler);

    public int getRepeatCount()
    {
        return repeatCount;
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