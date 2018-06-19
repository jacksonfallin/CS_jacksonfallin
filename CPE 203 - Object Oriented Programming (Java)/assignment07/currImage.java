/*import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

final class currImage
{
 public static PImage getCurrentImage(Object entity)
   {
      if (entity instanceof Background)
      {
         return ((Background)entity).images
            .get(((Background)entity).imageIndex);
      }

      else if (entity instanceof Entity)
      {
         return ((Entity)entity).getimages().get(((Entity)entity).getImageIndex());
      }
      
      else
      {
         throw new UnsupportedOperationException(
            String.format("getCurrentImage not supported for %s",
            entity));
      }
   }
}*/