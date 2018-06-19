import processing.core.PImage;

import java.util.List;

public abstract class MinerAbstract  extends  MovementAbst 
{

    public MinerAbstract (String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int repeatCount)
    {
        super(id,position,images,resourceLimit,repeatCount,actionPeriod,animationPeriod,0);
    }

    public void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Entity miner = this.getPosition().createMinerNotFull(this.getId(), this.getResourceLimit(),this.getActionPeriod(),this.getAnimationPeriod(),this.getimages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        ((MovementEntity)miner).scheduleActions(scheduler, world, imageStore);
    }
}


