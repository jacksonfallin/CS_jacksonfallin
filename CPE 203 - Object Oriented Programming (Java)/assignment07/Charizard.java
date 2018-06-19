import java.util.List;
import java.util.Optional;
import processing.core.PImage;

public class Charizard extends MovementEntity 
{

    private final String EAT = "eat";

    public String id;

    public Charizard(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) 
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }


    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) 
    {
        if (Point.adjacent(this.getPosition(), target.getPosition())){
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()){
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public boolean instanceOf(Entity entity)
    {
        return (entity instanceof Blacksmith);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) 
    {
        Optional<Entity> blobTarget = world.findNearest(world, this.getPosition(), e -> e instanceof MinerNotFull);
        long nextPeriod = getActionPeriod();

        if (blobTarget.isPresent()) 
        {
            Point tgtPos = blobTarget.get().getPosition();
            if (moveTo(world, blobTarget.get(), scheduler)) 
            {
                Quake quake = Point.createQuake(tgtPos, imageStore.getImageList(EAT));
                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this, Point.createActivityAction(this, world, imageStore),nextPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) 
    {
        scheduler.scheduleEvent(this, Point.createActivityAction(this, world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, Point.createAnimationAction(this, 0),5);
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

    @Override
    public Point nextPosition(WorldModel world, Point destPos)
    {
        SingleStepPathingStrategy strategy = new SingleStepPathingStrategy();
        List<Point> x = strategy.computePath(getPosition(), destPos,
                ((Point point) -> !world.isOccupied(point) && world.withinBounds(point)),
                (Point position) -> potentialNeighbors(position));
        if (x.size() == 0)
        {
            return getPosition();
        } 

        else 
        {
            return x.get(x.size() - 1);
        }
    }
}