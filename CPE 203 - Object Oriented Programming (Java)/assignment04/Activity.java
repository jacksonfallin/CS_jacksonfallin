import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Activity extends AbstractAction 
{
    public Entity entity;
    public WorldModel world;
    public ImageStore imageStore;

    public Activity(Entity entity, WorldModel world, ImageStore imageStore)
    {
        super(entity);
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler)
    {
        ((KineticEntity)this.getEntity()).executeActivity(world, imageStore, scheduler);
    }

    public static Action createActivityAction(Entity entity, WorldModel world,
        ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore);
    }

    /*public void executeActivityAction(EventScheduler scheduler)
    {
        switch (entity.kind)
        {
        case MINER_FULL:
            entity.executeMinerFullActivity(this.world,
                this.imageStore, scheduler);
            break;

        case MINER_NOT_FULL:
            entity.executeMinerNotFullActivity(this.world,
                this.imageStore, scheduler);
            break;

        case ORE:
            entity.executeOreActivity(this.world, this.imageStore,
                scheduler);
            break;

        case ORE_BLOB:
            entity.executeOreBlobActivity(this.world,
                this.imageStore, scheduler);
            break;

        case QUAKE:
            entity.executeQuakeActivity(this.world, this.imageStore,
                scheduler);
            break;

        case VEIN:
            entity.executeVeinActivity(this.world, this.imageStore,
                scheduler);
            break;

        default:
            throw new UnsupportedOperationException(
                String.format("executeActivityAction not supported for %s",
                this.entity.kind));
        }
    }*/
}