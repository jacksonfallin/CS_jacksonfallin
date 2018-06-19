import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

final class Background
{
    public String id;
    public List<PImage> images;
    public int imageIndex;

    public static final Random rand = new Random();

    public static final int PROPERTY_KEY = 0;
    public static final String BGND_KEY = "background";
    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    public Background(String id, List<PImage> images)
    {
        this.id = id;
        this.images = images;
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

    public boolean parseBackground(String [] properties,
        WorldModel world, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt,
                new Background(id, Entity.getImageList(imageStore, id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    public boolean processLine(String line, WorldModel world,
        ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        /*if (properties.length > 0)
        {
            return parseBackground(properties, world, imageStore);
        }*/
        return true;
    }
}
