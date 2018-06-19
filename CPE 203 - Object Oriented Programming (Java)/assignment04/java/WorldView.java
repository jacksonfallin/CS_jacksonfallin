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
    public PApplet screen;
    public WorldModel world;
    public int tileWidth;
    public int tileHeight;
    public Viewport viewport;

    public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
        int tileWidth, int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public int clamp(int value, int low, int high)
    {
        return Math.min(high, Math.max(value, low));
    }

    public void shiftView(int colDelta, int rowDelta)
    {
        int newCol = clamp(this.viewport.col + colDelta, 0,
            this.world.numCols - this.viewport.numCols);
        int newRow = clamp(this.viewport.row + rowDelta, 0,
            this.world.numRows - this.viewport.numRows);

        this.viewport.shift(newCol, newRow);
    }

    public void drawBackground()
    {
        for (int row = 0; row < this.viewport.numRows; row++)
        {
            for (int col = 0; col < this.viewport.numCols; col++)
            {
                Point worldPoint = this.viewport.viewportToWorld(col, row);
                Optional<PImage> image = this.world.getBackgroundImage(
                    worldPoint);
                if (image.isPresent())
                {
                   this.screen.image(image.get(), col * this.tileWidth,
                        row * this.tileHeight);
                }
            }
        }
    }

    public void drawEntities()
    {
        for (Entity entity : this.world.entities)
        {
            Point pos = entity.position;

            if (this.viewport.contains(pos))
            {
                Point viewPoint = this.viewport.worldToViewport(pos.x, pos.y);
                this.screen.image(getCurrentImage(entity),
                    viewPoint.x * this.tileWidth, viewPoint.y * this.tileHeight);
            }
        }
    }

    public static PImage getCurrentImage(Object entity)
    {
        if (entity instanceof Background)
        {
            return ((Background)entity).images
                .get(((Background)entity).imageIndex);
        }
        else if (entity instanceof Entity)
        {
            return ((Entity)entity).images.get(((Entity)entity).imageIndex);
        }
        else
        {
            throw new UnsupportedOperationException(
                String.format("getCurrentImage not supported for %s",
                entity));
        }
    }

    public void drawViewport()
    {
        this.drawBackground();
        this.drawEntities();
    }
}
