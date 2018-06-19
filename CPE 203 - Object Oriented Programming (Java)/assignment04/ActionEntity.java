public interface ActionEntity extends Animatable
{
	void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
	int getactionPeriod();
	void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}