public abstract class ActionAbstract implements Action
{
   private WorldModel world;
   private ImageStore imageStore;
   private int repeatCount;

   public ActionAbstract(WorldModel world, ImageStore imageStore, int repeatCount)
   {
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }

   public int repeatCount()
   {
   	return repeatCount;
   }

   public WorldModel world()
   {
   	return world;
   }

   public ImageStore imageStore()
   {
   	return imageStore;
   }
}