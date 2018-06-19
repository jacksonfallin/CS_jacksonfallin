import java.util.List;
import processing.core.PImage;

import java.util.Optional;
import java.util.LinkedList;

public class OreBlob extends MovementEntity
{

    private final String QUAKE_KEY = "quake";


    public OreBlob(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Point.createActivityAction(this, world, imageStore), getActionPeriod());
        scheduler.scheduleEvent(this, Point.createAnimationAction(this, 0), getAnimationPeriod());
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), ((Vein)target).getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, ((Vein)target).getPosition());

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

    public boolean instanceOf(Entity entity)
    {
        return (entity instanceof Vein);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(world, this.getPosition(), e -> e instanceof Vein);
        long nextPeriod = getActionPeriod();

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveTo(world, (Vein)blobTarget.get(), scheduler))
            {
                Quake quake = Point.createQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, Point.createActivityAction(this, world, imageStore), nextPeriod);
    }

    public boolean isAnythingThere(WorldModel world, Point destPos)
    {
        return (world.isOccupied(destPos) && !(world.getOccupant(destPos).get() instanceof  Ore));
    }

    @Override
    protected void modularMethod2(WorldModel world, ImageStore imageStore, EventScheduler scheduler) { }

    @Override
    protected void modularMethod3(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Optional<Entity> thing, long nextPeriod) 
    {
        /*Point tgtPos = thing.get().getgetPosition();

        Entity quake = tgtPos.createQuake(
                imageStore.getImageList( Quake.QUAKE_KEY));

        world.addEntity(quake);
        ((MovementEntity)quake).scheduleActions(scheduler, world, imageStore);*/
    }
}