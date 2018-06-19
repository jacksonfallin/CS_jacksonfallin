import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import java.util.*;

public class PartOneTestCases
{
    public static final double DELTA = 0.00001;

    @Test
    public void testCircleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getCenter", "getRadius");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Point.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[0]);

        verifyImplSpecifics(Circle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testRectangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getTopLeft", "getBottomRight");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Point.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[0]);

        verifyImplSpecifics(Rectangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testPolygonImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getPoints");

        final List<Class> expectedMethodReturns = Arrays.asList(
            List.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[][] {new Class[0]});

        verifyImplSpecifics(Polygon.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testUtilImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "perimeter", "perimeter", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            double.class, double.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[] {Circle.class},
            new Class[] {Polygon.class},
            new Class[] {Rectangle.class});

        verifyImplSpecifics(Util.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testPerimCircle()
    {
        Circle T = new Circle(new Point(0.0, 0.0), 1);
        double d = Util.perimeter(T);
        assertEquals(6.283185307179586, d, DELTA);
    }

    @Test
    public void testPerimRectangle()
    {
        Rectangle T = new Rectangle(new Point(5.0, 5.0), new Point(-5.0, 3.0));
        double d = Util.perimeter(T);
        assertEquals(24.0, d, DELTA);
    }

    @Test
    public void testPerimPolygon()
    {
        ArrayList<Point> points = new ArrayList<Point>(); 
        points.add(new Point(1,1));
        points.add(new Point(2,0));
        points.add(new Point(4,4));
        double d = Util.perimeter(new Polygon(points));
        assertEquals(3.0, d, DELTA);
    }

    @Test 
    public void testWhichIsBigger()
    {
        Circle c = new Circle(new Point(-5.0, 5.0), 4.0);

        Rectangle r = new Rectangle(new Point(7.0, 2.0), new Point(-1.0, -1.0));

        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(2.0, 0.0));
        points.add(new Point(2.0, 1.0));
        points.add(new Point(1.0, 3.0));
        points.add(new Point(2.0, 6.0));
        Polygon p = new Polygon(points);

        double ans = Bigger.whichIsBigger(c, r, p);
        assertEquals(ans, 4.0, DELTA);
    }

    private static void verifyImplSpecifics(
        final Class<?> clazz,
        final List<String> expectedMethodNames,
        final List<Class> expectedMethodReturns,
        final List<Class[]> expectedMethodParameters)
        throws NoSuchMethodException
    {
        assertEquals("Unexpected number of public fields",
            0, clazz.getFields().length);

        final List<Method> publicMethods = Arrays.stream(
            clazz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .collect(Collectors.toList());

        assertEquals("Unexpected number of public methods",
            expectedMethodNames.size(), publicMethods.size());

        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodReturns.size());
        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodParameters.size());

        for (int i = 0; i < expectedMethodNames.size(); i++)
        {
            Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                expectedMethodParameters.get(i));
            assertEquals(expectedMethodReturns.get(i), method.getReturnType());
        }
    }
}
