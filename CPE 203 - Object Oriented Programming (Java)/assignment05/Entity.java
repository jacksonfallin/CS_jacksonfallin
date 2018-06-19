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

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }

    public int getImageIndex() 
    { 
        return imageIndex; 
    }

    public void setImageIndex(int imageIndex)
    {
        this.imageIndex = imageIndex;
    }

    public String getid() 
    { 
        return id; 
    }

    public String setid(String id) 
    { 
        return this.id = id; 
    }

    public List<PImage> getImageList()
    {
        return this.images;
    }

    public PImage getCurrentImage()
    {
       return images.get(imageIndex);
    }

    public Point nextPosition(WorldModel world,
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
}