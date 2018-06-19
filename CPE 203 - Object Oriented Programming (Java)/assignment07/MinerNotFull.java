import java.util.Optional;
import java.util.LinkedList;
import processing.core.PImage;
import java.util.List;

public class MinerNotFull extends MovementEntity
{


    public MinerNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);

    }

    public void shift(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Point pos = getPosition();

        Ashe ashe = Point.createAshe(id(), getPosition(),  getActionPeriod(), getAnimationPeriod(), imageStore.getImageList("ashe"));
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(ashe);

        ashe.scheduleActions(scheduler, world, imageStore);

    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(world, this.getPosition(), e -> e instanceof Ore);


        if (!notFullTarget.isPresent() || !moveTo(world, notFullTarget.get(), scheduler) || !alter(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, Point.createActivityAction(this, world, imageStore), getActionPeriod());
        }
    }

    @Override
    public boolean instanceOf(Entity entity) 
    {
        return (entity instanceof Ore);
    }

    public boolean alter(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount() >= resourceLimit())
        {
            MinerFull miner = Point.createMinerFull(id(), resourceLimit(), getPosition(), getActionPeriod(), getAnimationPeriod(), images());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    @Override
    public boolean isAnythingThere(WorldModel world, Point destPos) 
    {
        return world.isOccupied(destPos);
    }


    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), ((Ore)target).getPosition()))
        {
            this.setResourceCount(resourceCount() + 1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, ((Ore)target).getPosition());

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

    /*@Override //For some reason this works 
    public void modularMethod1(WorldModel world, ImageStore imageStore, EventScheduler scheduler) 
    {

    }*/

    @Override// For some reason this works 
    public void modularMethod2(WorldModel world, ImageStore imageStore, EventScheduler scheduler) 
    {

    }

    @Override
    // This is part of the scheduleEvents
    protected void modularMethod3(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Optional<Entity> thing, long nextPeriod) 
    {
                /*scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());*/
    }
}


