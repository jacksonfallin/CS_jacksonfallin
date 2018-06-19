
import processing.core.PApplet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;



final class WorldView
{
   private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world, int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }

   public  int clamp(int value, int low, int high)
    {
        return Math.min(high, Math.max(value, low));
    }

    public  void shiftView(int colDelta, int rowDelta)
    {
        int newCol = clamp(this.viewport.getCol() + colDelta, 0,
                this.world.getnumCols() - this.viewport.getNumCols());
        int newRow = clamp(this.viewport.getRow() + rowDelta, 0,
                this.world.getnumRows() - this.viewport.getNumRows());

        this.viewport.shift (newCol, newRow);
    }

    public Viewport getViewPort() 
    {
        return this.viewport;
    }

    public Point rowPoint(int col, int row)
    {
        return viewport.viewportToWorld(col, row);
    }

    public  void drawBackground()
    {
        for (int row = 0; row < this.viewport.getNumRows(); row++)
        {
            for (int col = 0; col < this.viewport.getNumCols(); col++)
            {
                Point worldPoint = this.viewport.viewportToWorld(col, row);
                Optional<PImage> image = this.world.getBackgroundImage(worldPoint);
                if (image.isPresent())
                {
                    this.screen.image(image.get(), col * this.tileWidth,
                            row * this.tileHeight);
                }
            }
        }
    }

    public  void drawEntities()
    {
        for (Entity entity : this.world.getEntities())
        {
            Point pos = entity.position();

            if (this.viewport.contains( pos))
            {
                Point viewPoint = this.viewport.worldToViewport(pos.x, pos.y);
                this.screen.image(entity.getCurrentImage(),
                        viewPoint.x * this.tileWidth, viewPoint.y * this.tileHeight);
            }
        }
    }

    public void drawViewport()
    {
        drawBackground();
        drawEntities();
    }

}