import java.awt.*;
import java.util.Optional;
import java.util.LinkedList;
import processing.core.PImage;
import java.util.List;

final class MinerFull extends MovementEntity
{

    private  int resourceLimit;

    public MinerFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public boolean instanceOf(Entity entity)
    {
        return (entity instanceof Blacksmith);
    }

    public void shift(WorldModel world, EventScheduler scheduler, ImageStore imageStore) 
    {
        Point pos = getPosition();

        Ashe ashe = Point.createAshe(id, getPosition(), getActionPeriod(), getAnimationPeriod(), imageStore.getImageList("ashe"));
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(ashe);
        ashe.scheduleActions(scheduler, world, imageStore);

    }


    public boolean isAnythingThere(WorldModel world, Point destPos)
    {
        return world.isOccupied(destPos);
    }

    //protected void modularMethod1(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {}

    @Override
    protected void modularMethod2(WorldModel world, ImageStore imageStore, EventScheduler scheduler) 
    {
        //scheduler.scheduleEvent(this,Activity.createActivityAction(this,world,imageStore),this.getActionPeriod());
    }

    protected void modularMethod3(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Optional<Entity> thing, long nextPeriod) 
    {

    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(world, this.getPosition(), e ->  e instanceof Blacksmith);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    Point.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }


    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(getPosition(), (target).getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, (target).getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }

    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = Point.createMinerNotFull(id(), resourceLimit(),
                getPosition(), getActionPeriod(), getAnimationPeriod(),
                images());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }
}



