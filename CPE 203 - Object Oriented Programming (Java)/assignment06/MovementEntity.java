import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public abstract class MovementEntity extends AnimationAbst
{
    private int resourceLimit;
    private int resourceCount;

    public MovementEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) 
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public void setResourceCount(int count)
    {
        resourceCount = count;
    }

    public int resourceLimit()
    {
        return resourceLimit;
    }

    public int resourceCount()
    {
        return resourceCount;
    }

    public  Optional<Entity> nearestEntity(List<Entity> entities,
                                           Point pos)
    {
        if (entities.isEmpty())
        {
            return Optional.empty();
        }
        else
        {
            Entity nearest = entities.get(0);
            int nearestDistance = Point.distanceSquared(nearest.position(), pos);

            for (Entity other : entities)
            {
                int otherDistance = Point.distanceSquared(other.position(), pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public Optional<Entity> findnearest(WorldModel world, Point pos, Entity e)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : world.getEntities())
        {
            if (instanceOf(entity))
            {
                ofType.add(entity);
            }
        }
        return nearestEntity(ofType, pos);
    }

    /*public boolean moveToFull (WorldModel world, Entity target, EventScheduler scheduler)
    {
        System.out.println(getId());
        if (Point.adjacent(getPosition(), (target).getPosition()))
        {
            moveTo(world,target,scheduler);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity((Entity) this, nextPos);
            }
            return false;
        }
    }*/

    public List<Point> potentialNeighbors(Point point) 
    {
        List<Point> direction = new ArrayList<>();

        Point up = new Point(point.x + 0, point.y + 1);
        Point down = new Point(point.x + 0, point.y - 1);
        Point left = new Point(point.x - 1, point.y + 0);
        Point right = new Point(point.x + 1, point.y + 0);

        direction.add(up);
        direction.add(down);
        direction.add(left);
        direction.add(right);
        
        return direction;
    }

    protected static Predicate<Point> canPassThrough(WorldModel world)
    {
        return p -> (!world.isOccupied(p) && world.withinBounds(p));
    }

    public Point nextPosition(WorldModel world, Point destPos) 
    {
        AStarPathingStrategy asa = new AStarPathingStrategy();

        List<Point> x = asa.computePath(position(), destPos,((Point point) -> !world.isOccupied(point) && world.withinBounds(point)), (Point position) -> potentialNeighbors(position));

        if (x.size() == 0) 
        {
            return position();
        } 

        else 
        {
            return x.get(x.size() - 1);
        }
    }

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
    protected abstract boolean instanceOf(Entity entity);
    protected abstract boolean isAnythingThere(WorldModel world, Point destPos);
    //protected abstract void modularMethod1(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    protected abstract void modularMethod2(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    protected abstract void modularMethod3(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Optional<Entity> thing, long nextPeriod);
}