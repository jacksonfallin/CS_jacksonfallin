public class Animation extends Action
{
    public AnimationAbst entity;
    private int repeatCount;

    public Animation(AnimationAbst  entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        super(world, imageStore, repeatCount);
        this.entity = entity;
    }

    /*public static Animation createAnimationAction(Entity entity, int repeatCount)
    {
        return new Animation(entity, repeatCount);
    }*/

    public int getRepeatCount() 
    {
        return repeatCount;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage() ;

        if (getRepeatCount() != 1)
        {
            scheduler.scheduleEvent(entity,
                    Point.createAnimationAction(entity,
                            Math.max(getRepeatCount() - 1, 0)),
                    entity.getAnimationPeriod());
        }
    }
}
