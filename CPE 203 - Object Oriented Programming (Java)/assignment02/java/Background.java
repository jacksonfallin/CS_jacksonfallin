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

    public Background(String id, List<PImage> images)
    {
        this.id = id;
        this.images = images;
    }
}
