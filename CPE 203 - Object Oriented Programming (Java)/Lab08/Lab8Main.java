import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.Comparator;
import java.util.List;

public class Lab8Main {

    //
    // Do not modify this static field declaration.  An automated test
    // will use it.  This map holds a count of the number of times each
    // point is seen.
    //
    public static MyMap<Point, Integer> pointCounts;

    /**
     * Adds one to the count for point p in pointCounts.  If p wasn't
     * in the map, adds it (with a count of 1).  This method will be
     * called by an automated test.
     */
    public static void addPoint(Point p) 
    {

        if (pointCounts.get(p) != null) {
            int count = pointCounts.get(p);
            pointCounts.put(p, count + 1);
        } else
            pointCounts.put(p, 1);
    }

    /**
     * Calls consumer.accept() on every entry in pointCounts.  Do not
     * modify this method.
     */
    public static void
    visitPointCounts(Consumer<MyMap.MyMapEntry<Point, Integer>> consumer) 
    {
        for (MyMap.MyMapEntry<Point, Integer> entry : pointCounts.getEntries()) 
        {
            consumer.accept(entry);
        }
    }

    /**
     * Calls consumer.accept on every entry in pointCounts, sorted
     * by the given comparator.
     */
    public static void
    visitPointCountsSorted(
            Consumer<MyMap.MyMapEntry<Point, Integer>> consumer,
            Comparator<MyMap.MyMapEntry<Point, Integer>> comparator) 
    {
        List<MyMap.MyMapEntry<Point, Integer>> entries = pointCounts.getEntries();
        Collections.sort(entries, comparator);
        for (MyMap.MyMapEntry<Point, Integer> entry : entries) 
        {
            consumer.accept(entry);
        }
    }

    /**
     * Calls consumer.accept on every bucket of your map in order, starting
     * from bucket 0.
     */
    public static void
    visitBuckets(BiConsumer<Integer, List<MyMap.MyMapEntry<Point, Integer>>> consumer) 
    {
        for (int index = 0; index < pointCounts.getBuckets().size(); index++) 
        {
            consumer.accept(index, pointCounts.getBuckets().get(index));
        }
    }


    //
    // Main method.  You may modify this.
    //
    public static void main(String[] args) {
        // You might care to see what happens if you increase the
        // number of buckets to 20, or 40.
        pointCounts = new MyMap<>(10);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                addPoint(new Point(i, j));
                if (i == j) {
                    for (int k = 0; k < i; k++) {
                        addPoint(new Point(i, j));
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Visiting point counts:");
        visitPointCounts(
                (MyMap.MyMapEntry<Point, Integer> entry) -> {
                    System.out.println("    " + entry.key + " seen " +
                            entry.value + " times.");
                }
        );

        System.out.println();
        System.out.println("Visiting point counts, sorted by y, then x:");
        visitPointCountsSorted(
                (MyMap.MyMapEntry<Point, Integer> entry) -> {
                    System.out.println("    " + entry.key + " seen " +
                            entry.value + " times.");
                },
                (MyMap.MyMapEntry<Point, Integer> a, MyMap.MyMapEntry<Point, Integer> b) -> {
                    int byY = Integer.compare(a.key.y, b.key.y);
                    if (byY != 0) {
                        return byY;
                    }
                    return Integer.compare(a.key.x, b.key.x);
                }
        );

        System.out.println();
        System.out.println("Visiting buckets:");
        visitBuckets(
                (Integer bucketNum, List<MyMap.MyMapEntry<Point, Integer>> contents) -> {
                    System.out.println("    bucket " + bucketNum +
                            " contains " + contents.toString());
                }
        );

        System.out.println();
        System.out.println("Done.");
        System.out.println();
    }
}