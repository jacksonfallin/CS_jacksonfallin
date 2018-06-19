import processing.core.PImage;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

final class Point
{
   public final int x;
   public final int y;


    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString()
   {
      return "(" + x + "," + y + ")";
   }

    public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

  public static boolean adjacent(Point p1, Point p2)
  {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
                (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
  }

    public static int distanceSquared(Point p1, Point p2)
    {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    public Optional<Entity> nearestEntity(List<Entity> entities)
    {
        if (entities.isEmpty())
        {
            return Optional.empty();
        }
        else
        {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.position(), this);

            for (Entity other : entities)
            {
                int otherDistance = distanceSquared(other.position(), this);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public static Charizard createCharizard(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Charizard(id, position, images,0, 0, actionPeriod, animationPeriod);
    }


    public static Ashe createAshe(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Ashe(id, position, images,0, 0, actionPeriod, animationPeriod);
    }


    public static Animation createAnimationAction(AnimationAbst entity, int repeatCount)
    {
        return new Animation(entity, null, null, repeatCount);
    }

    public static Activity createActivityAction(ActivityAbst entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    public static Blacksmith createBlacksmith(String id, Point position, List<PImage> images)
    {
        return new Blacksmith(id, position, images);
    }

    public static MinerFull createMinerFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MinerFull(id, position, images,
                resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public static MinerNotFull createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MinerNotFull(id, position, images,
                resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static Obstacle createObstacle(String id, Point position, List<PImage> images)
    {
        return new Obstacle(id, position, images, 0, 0);
    }

    public static Ore createOre(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Ore(id, position, images, actionPeriod);
    }

    public static OreBlob createOreBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new OreBlob(id, position, images,
                0, 0, actionPeriod, animationPeriod);
    }

    public static Quake createQuake(Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public static Vein createVein(String id, Point position, int actionPeriod,
                                  List<PImage> images)
    {
        return new Vein(id, position, images, actionPeriod);
    }

    public static void load(Scanner in, WorldModel world, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                if (!processLine(in.nextLine(), world, imageStore))
                {
                    System.err.println(String.format("invalid entry on line %d", lineNumber));
                }
            }

            catch (NumberFormatException e)
            {
                System.err.println(String.format("invalid entry on line %d", lineNumber));
            }

            catch (IllegalArgumentException e)
            {
                System.err.println(String.format("issue on line %d: %s", lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }

     public static boolean processLine(String line, WorldModel world, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            switch (properties[VirtualWorld.PROPERTY_KEY])
            {
                case VirtualWorld.BGND_KEY:
                    return VirtualWorld.parseBackground(properties, world, imageStore);
                case VirtualWorld.MINER_KEY:
                    return VirtualWorld.parseMiner(properties, world, imageStore);
                case VirtualWorld.OBSTACLE_KEY:
                    return VirtualWorld.parseObstacle(properties, world, imageStore);
                case VirtualWorld.ORE_KEY:
                    return VirtualWorld.parseOre(properties, world, imageStore);
                case VirtualWorld.SMITH_KEY:
                    return VirtualWorld.parseSmith(properties, world, imageStore);
                case VirtualWorld.VEIN_KEY:
                    return VirtualWorld.parseVein(properties, world, imageStore);
            }
        }

      return false;
    }
    
}