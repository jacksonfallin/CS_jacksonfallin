import java.util.List;
import processing.core.PImage;

public class Obstacle extends Entity 
{

	private static final String OBSTACLE_KEY = "car";
	private static final int OBSTACLE_NUM_PROPERTIES = 4;
	private static final int OBSTACLE_ID = 1;
	private static final int OBSTACLE_COL = 2;
	private static final int OBSTACLE_ROW = 3;

    public Obstacle(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount)
    {
        super(id, position, images);
    }
}