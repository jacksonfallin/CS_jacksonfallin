import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public abstract class Entity
{
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;
    public static final Random rand = new Random();

    public Entity()
    {
        this.id = "";
        this.position = null;
        this.images = null;
        this.imageIndex = 0;
        this.resourceLimit = 0;
        this.resourceCount = 0;
        this.actionPeriod = 0;
        this.animationPeriod = 0; 
    }

    public Entity(String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
    {
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

    public static List<PImage> getImageList(ImageStore imageStore, String key)
    {
        return imageStore.images.getOrDefault(key, imageStore.defaultImages);
    }

    public Point getPosition()
    {
    	return position;
    }

    public void setPosition(Point position)
    {
    	this.position = position;
    }

    public PImage getCurrentImage(Object entity)
    {
        if (entity instanceof Background)
        {
            return ((Background)entity).images
                .get(((Background)entity).imageIndex);
        }
        else if (entity instanceof Entity)
        {
            return ((Entity)entity).images.get(((Entity)entity).imageIndex);
        }
        else
        {
            throw new UnsupportedOperationException(
                String.format("getCurrentImage not supported for %s",
                entity));
        }
    }

    public Point getposition() 
    { 
        return position;
    }
    public void setposition(Point point) 
    { 
        this.position = point; 
    }
    public List<PImage> getimages() 
    { 
        return images; 
    }
    public String getid() 
    { 
        return id; 
    }
    public int getImageIndex() 
    { 
        return imageIndex; 
    }

}