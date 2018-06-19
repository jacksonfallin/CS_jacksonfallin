import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

final class Entity
{
    public EntityKind kind;
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;

    public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;
    public static final int ORE_REACH = 1;

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 4;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;

    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;

    public static final Random rand = new Random();

    public Entity(EntityKind kind, String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
    {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void executeMinerFullActivity(WorldModel world,
        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.position,
            EntityKind.BLACKSMITH);

        if (fullTarget.isPresent() &&
            this.moveToFull(world, fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
        }
    }

    public void executeMinerNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.position,
            EntityKind.ORE);

        if (!notFullTarget.isPresent() ||
            !this.moveToNotFull(world, notFullTarget.get(), scheduler) ||
            !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
        }
    }

    public void executeOreActivity(WorldModel world,
        ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;    // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity blob = pos.createOreBlob(this.id + BLOB_ID_SUFFIX,
            this.actionPeriod / BLOB_PERIOD_SCALE,
            BLOB_ANIMATION_MIN +
                rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
            imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }

    public void executeOreBlobActivity(WorldModel world,
        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(
            this.position, EntityKind.VEIN);
        long nextPeriod = this.actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().position;

            if (this.moveToOreBlob(world, blobTarget.get(), scheduler))
            {
                Entity quake = tgtPos.createQuake(
                    imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            nextPeriod);
    }

    public void executeQuakeActivity(WorldModel world,
        ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public void executeVeinActivity(WorldModel world,
        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            Entity ore = openPt.get().createOre(ORE_ID_PREFIX + this.id,
                ORE_CORRUPT_MIN +
                    rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                imageStore.getImageList(ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            this.actionPeriod);
    }

     public void scheduleActions(EventScheduler scheduler,
        WorldModel world, ImageStore imageStore)
    {
        switch (this.kind)
        {
        case MINER_FULL:
            scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
            scheduler.scheduleEvent(this, this.createAnimationAction(0),
                this.getAnimationPeriod());
            break;

        case MINER_NOT_FULL:
            scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
            scheduler.scheduleEvent(this,
                this.createAnimationAction(0), this.getAnimationPeriod());
            break;

        case ORE:
            scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
            break;

        case ORE_BLOB:
            scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
            scheduler.scheduleEvent(this,
                this.createAnimationAction(0), this.getAnimationPeriod());
            break;

        case QUAKE:
            scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
            scheduler.scheduleEvent(this,
                this.createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());
            break;

        case VEIN:
           scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
            break;

        default:
        }
    }

    public boolean transformNotFull(WorldModel world,
        EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit)
        {
            Entity miner = this.position.createMinerFull(this.id, this.resourceLimit,
                this.actionPeriod, this.animationPeriod,
                this.images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void transformFull(WorldModel world,
        EventScheduler scheduler, ImageStore imageStore)
    {
        Entity miner = this.position.createMinerNotFull(this.id, this.resourceLimit,
            this.actionPeriod, this.animationPeriod,
            this.images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    public boolean moveToNotFull(WorldModel world,
        Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.position))
        {
            this.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = this.nextPositionMiner(world, target.position);

            if (!this.position.equals(nextPos))
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

    public boolean moveToFull(WorldModel world,
        Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.position))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPositionMiner(world, target.position);

            if (!this.position.equals(nextPos))
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

    public boolean moveToOreBlob(WorldModel world,
        Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.position))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPositionOreBlob(world, target.position);

            if (!this.position.equals(nextPos))
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

    public Point nextPositionMiner(WorldModel world,
        Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
            this.position.y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x,
                this.position.y + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public Point nextPositionOreBlob(WorldModel world,
        Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
            this.position.y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
            (occupant.isPresent() && !(occupant.get().kind == EntityKind.ORE)))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x, this.position.y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                (occupant.isPresent() && !(occupant.get().kind == EntityKind.ORE)))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public Action createAnimationAction(int repeatCount)
    {
        return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
    }

    public Action createActivityAction(WorldModel world,ImageStore imageStore)
    {
        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
    }

    public int getAnimationPeriod()
    {
        switch (this.kind)
        {
        case MINER_FULL:
        case MINER_NOT_FULL:
        case ORE_BLOB:
        case QUAKE:
            return this.animationPeriod;
        default:
            throw new UnsupportedOperationException(
                String.format("getAnimationPeriod not supported for %s",
                this.kind));
        }
    }

}
