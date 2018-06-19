import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class Animation extends AbstractAction
{
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public Animation(Entity entity, int repeatCount)
    {
        super(entity);
        this.repeatCount = repeatCount;
    }

    public static Animation createAnimationAction(Entity entity, int repeatCount)
    {
        return new Animation(entity, repeatCount);
    }

    public void executeAction(EventScheduler scheduler)
    {
        ((AnimationEntity)this.getEntity()).nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.getEntity(),
                createAnimationAction(this.getEntity(),
                Math.max(this.repeatCount - 1, 0)),
                ((AnimationEntity)this.getEntity()).getanimationPeriod());
        }
    }
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
