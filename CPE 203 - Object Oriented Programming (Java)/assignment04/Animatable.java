import java.util.List;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public interface Animatable 
{
	public int getAnimationPeriod(Entity entity);
	
	public void scheduleActions(Entity entity, EventScheduler scheduler,
        WorldModel world, ImageStore imageStore);

	public boolean processLine(String line, WorldModel world,
        ImageStore imageStore);

	Point getposition();

	void setposition(Point point);

	List<PImage> getimages();

	String getid();
	
	int getImageIndex();
}