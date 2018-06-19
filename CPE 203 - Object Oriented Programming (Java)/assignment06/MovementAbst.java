import processing.core.PImage;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class MovementAbst extends AnimationAbst implements Location 
{

    protected List<Point> path = new ArrayList<>();
    protected int numRows;
    protected int numCols;

    public MovementAbst(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int repeatCount)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, repeatCount);
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

      public boolean withinBounds(Point pos)
    {
        return pos.y >= 0 && pos.y < numRows &&
                pos.x >= 0 && pos.x < numCols;
    }


    protected static BiPredicate<Point, Point> withinReach()
    {
        return (Point p1, Point p2) -> p1.adjacent(p2);

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
            int nearestDistance = nearest.getPosition().distanceSquared(pos);

            for (Entity other : entities)
            {
                int otherDistance = other.getPosition().distanceSquared(pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public boolean moveToFull (WorldModel world, Entity target, EventScheduler scheduler)
    {
        System.out.println(getId());
        if (this.getPosition().adjacent(target.getPosition()))
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
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget;
        fullTarget = this.findnearest(world,this.getPosition(),
                this);
        long nextAction = this.getActionPeriod();

        if (bool(world,imageStore,scheduler,fullTarget))
        {
            modularMethod3(world,imageStore,scheduler,fullTarget,nextAction);
        }
        else
        {
            modularMethod2(world,imageStore,scheduler);
        }
        modularMethod1(world, imageStore, scheduler);
    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) 
    {


        AStarPathingStrategy asa = new AStarPathingStrategy();

        List<Point> x = asa.computePath(position, destPos,
                ((Point point) -> !world.isOccupied(point) && world.withinBounds(point)),
                (Point position) -> potentialNeighbors(position));

        if (x.size() == 0) 
        {
            return position;
        } 
        
        else 
        {
            return x.get(x.size() - 1);
        }
    }

    protected abstract boolean bool(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Optional<Entity> thing);
    protected abstract boolean instanceOf(Entity entity);
    protected abstract void moveTo(WorldModel world, Entity thing, EventScheduler scheduler);
    protected abstract boolean isAnythingThere(WorldModel world, Point destPos);

    protected abstract void modularMethod1(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    protected abstract void modularMethod2(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    protected abstract void modularMethod3(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Optional<Entity> thing, long nextPeriod);
}
