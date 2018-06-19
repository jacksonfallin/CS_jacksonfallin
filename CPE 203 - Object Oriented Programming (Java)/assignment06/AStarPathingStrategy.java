import java.util.function.Predicate;
import java.util.function.Function;
import static java.lang.Math.abs;
import java.util.*;

public class AStarPathingStrategy implements PathingStrategy 
{

    //function A*(start, goal)
    @Override
    public List<Point> computePath(final Point start, final Point goal, Predicate<Point> canPassThrough, Function<Point, List<Point>> potentialNeighbors)
    {

        Map<Point, Integer> gScore = new HashMap<>();
        gScore.put(start, 0);

        Map<Point, Integer> fScore = new HashMap<>();
        fScore.put(start, hueristic(start, goal));

        List<Point> traveled = new ArrayList<>();

        List<Point> notTraveled = new ArrayList<>();
        notTraveled.add(start);

        Map<Point, Point> trackPrev = new HashMap<>();

        while (notTraveled.size() != 0)
        {
            Integer FScore = fScore.get(notTraveled.get(0));
            int currentIndex = 0;
            int i = 0;
            for (Point val : notTraveled)
            {
                if (fScore.get(val) < FScore)
                {
                    currentIndex = i;
                    FScore = fScore.get(val);
                }

                i++;
            };

            Point currentPos = notTraveled.get(currentIndex);

            if (currentPos.equals(goal))
            {
                List<Point> theTrail = createTrail(trackPrev, currentPos);
                theTrail.remove(start);
                return theTrail;
            }

            notTraveled.remove(currentPos);
            traveled.add(currentPos);
            List<Point> direction = potentialNeighbors.apply(currentPos);

            for (Point neighbor : direction)
            {
                if (direction.contains(neighbor)) 
                {
                    gScore.put(neighbor, 10000);
                    fScore.put(neighbor, hueristic(neighbor, goal));
                }
            }

            for (Point neighbor : direction)
            {
                if (!canPassThrough.test(neighbor) && !neighbor.equals(goal))
                {
                    continue;
                }

                if (traveled.contains(neighbor))
                {
                    continue;
                }

                if (!notTraveled.contains(neighbor))
                {
                    notTraveled.add(neighbor);
                }

                Integer GScore = gScore.get(currentPos) + manhatten(currentPos, neighbor);
                
                if (GScore >= gScore.get(neighbor))
                {
                    continue;
                }

                trackPrev.put(neighbor, currentPos);
                gScore.put(neighbor, GScore);
                fScore.put(neighbor, gScore.get(neighbor) + hueristic(neighbor, goal));
            }
        }

        List<Point> failure = new ArrayList<>();
        failure.add(start);
        return failure;
    }

    private List<Point> createTrail(Map<Point, Point> point1, Point point2){
        List<Point> total_Trail = new ArrayList<>();
        total_Trail.add(point2);
        while (point1.containsKey(point2))
        {
            point2 = point1.get(point2);
            total_Trail.add(point2);
        }

        return total_Trail;
    }

    private static Integer manhatten(Point point1, Point point2)
    {
        int X = abs(point1.x - point2.x);
        int Y = abs(point1.y - point2.y);
        return X + Y;
    }

    private Integer hueristic(Point point1, Point point2)
    {
        return manhatten(point1, point2);
    }
}

