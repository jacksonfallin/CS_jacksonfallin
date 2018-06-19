import processing.core.PImage;

public interface Animation2 extends Animatable
{
	void nextImage();
	int getanimationPeriod();
}