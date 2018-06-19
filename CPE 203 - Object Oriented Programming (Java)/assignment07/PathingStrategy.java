import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

interface PathingStrategy
{
    List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, Function<Point, List<Point>> potentialNeighbors);
}
