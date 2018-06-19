import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public abstract class Entity
{

    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;

    public Entity(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public Point getPosition()
    {
    	return position;
    }
    public void setPosition(Point pos)
    {
    	position = pos;
    }

    public List<PImage> images()
    {
    	return images;
    }

    public int imageIndex()
    {
    	return imageIndex;
    }
    public void setImageIndex(int in)
    {
    	imageIndex = in;
    }

    public PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }

    public String id()
    {
    	return id;
    }
    public void setId(String ID)
    {
    	id = ID;
    }
}