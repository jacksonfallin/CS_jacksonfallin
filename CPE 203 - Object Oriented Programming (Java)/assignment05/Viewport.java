import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

final class Viewport
{
    public int row;
    public int col;
    public int numRows;
    public int numCols;

    public Viewport(int numRows, int numCols)
    {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public int getrow()
    {
        return row;
    }

    public int getcol()
    {
        return col;
    }

    public int getnumRow()
    {
        return numRows;
    }

    public int getnumCol()
    {
        return numCols; 
    }

    public void shift(int col, int row)
    {
        this.col = col;
        this.row = row;
    }

    public boolean contains(Point p)
    {
        return p.y >= this.row && p.y < this.row + this.numRows &&
            p.x >= this.col && p.x < this.col + this.numCols;
    }

    public Point viewportToWorld(int col, int row)
    {
        return new Point(col + this.col, row + this.row);
    }

    public Point worldToViewport(int col, int row)
    {
        return new Point(col - this.col, row - this.row);
    }
}
