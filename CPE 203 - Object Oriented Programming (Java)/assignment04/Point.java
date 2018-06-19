import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

final class Point
{
    public final int x;
    public final int y;

    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
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

    public boolean adjacent(Point p2)
    {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
            (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int distanceSquared(Point p2)
    {
        int deltaX = this.x - p2.x;
        int deltaY = this.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }
}
