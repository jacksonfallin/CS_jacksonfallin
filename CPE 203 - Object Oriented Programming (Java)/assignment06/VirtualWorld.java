import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import processing.core.*;

public final class VirtualWorld extends PApplet 
{
    public static final int TIMER_ACTION_PERIOD = 100;

    public static final int VIEW_WIDTH = 1200;
    public static final int VIEW_HEIGHT = 1180;
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int WORLD_WIDTH_SCALE = 2;
    public static final int WORLD_HEIGHT_SCALE = 2;

    public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    public static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    public static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    public static final String IMAGE_LIST_FILE_NAME = "imagelist";
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    public static final int DEFAULT_IMAGE_COLOR = 0x808080;

    public static final String LOAD_FILE_NAME = "gaia.sav";

    public static final String FAST_FLAG = "-fast";
    public static final String FASTER_FLAG = "-faster";
    public static final String FASTEST_FLAG = "-fastest";
    public static final double FAST_SCALE = 0.5;
    public static final double FASTER_SCALE = 0.25;
    public static final double FASTEST_SCALE = 0.10;

    public static final int PROPERTY_KEY = 0;

    public static final String BGND_KEY = "background";
    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    public static final String OBSTACLE_KEY = "car";
    public static final int OBSTACLE_NUM_PROPERTIES = 4;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;

    public static final String ORE_KEY = "fire";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;

    public static final String SMITH_KEY = "hydrant";
    public static final int SMITH_NUM_PROPERTIES = 4;
    public static final int SMITH_ID = 1;
    public static final int SMITH_COL = 2;
    public static final int SMITH_ROW = 3;

    public static final String VEIN_KEY = "house";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;


    public static double timeScale = 1.0;

    public ImageStore imageStore;
    public WorldModel world;
    public WorldView view;
    public EventScheduler scheduler;

    public long next_time;

    public void settings() 
    {
        size(VIEW_WIDTH / 2, VIEW_HEIGHT / 2);
    }

    public void setup() 
    {
        this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,TILE_WIDTH, TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        ImageStore.loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public static void loadWorld(WorldModel world, String filename, ImageStore imageStore) 
    {
        try 
        {
            Scanner in = new Scanner(new File(filename));
            Point.load(in, world, imageStore);
        } 

        catch (FileNotFoundException e) 
        {
            System.err.println(e.getMessage());
        }
    }

    public void draw() 
    {
        long time = System.currentTimeMillis();
        if (time >= next_time) 
        {
            this.scheduler.updateOnTime(time);
            next_time = time + TIMER_ACTION_PERIOD;
        }

        view.drawViewport();
    }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            view.shiftView(dx, dy);
        }
    }


    public static Background createDefaultBackground(ImageStore imageStore) 
    {
        return new Background(DEFAULT_IMAGE_NAME,
                imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) 
    {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    public static void parseCommandLine(String[] args) 
    {
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore) 
    {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof ActivityAbst)
                ((ActivityAbst) entity).scheduleActions(scheduler, world, imageStore);
        }
    }


    private Background fire() 
    {
        return new Background("fire", imageStore.getImageList("fire"));
    }

    public static void main(String [] args)
    {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }


    private Point getMouse() 
    {
        return new Point((mouseX / TILE_WIDTH ) + view.getViewPort().getCol(), (mouseY / TILE_HEIGHT) + view.getViewPort().getRow());
    }

    public void mousePressed()
    {
        Point point = view.rowPoint(mouseX / TILE_WIDTH, mouseY / TILE_HEIGHT);

        Random rand = new Random();
        for (Entity entity : world.getEntities()) 
        {
            if (entity.position().equals(getMouse())) 
            {
                return;
            }
        }
        
        Charizard charizard = Point.createCharizard("charizard", getMouse(), 4000, 2000, imageStore.getImageList("charizard"));

        world.tryAddEntity(charizard);
        charizard.scheduleActions(scheduler, world, imageStore);
        List<Point> neighbors = world.getNeighborPoints(point, 8);

        for (Point p : neighbors) 
        {

            int dist = Point.distanceSquared(point, p);
            if (dist < rand.nextInt(3) + 2) 
            {
                    if (world.isOccupied(p)) 
                    { 
                        if (world.getOccupancyCell(p) instanceof MinerNotFull) 
                        {
                            ((MinerNotFull) world.getOccupancyCell(p)).shift(world, scheduler, imageStore);
                        } 
                        else if (world.getOccupancyCell(p) instanceof MinerFull) 
                        {
                            ((MinerFull) world.getOccupancyCell(p)).shift(world, scheduler, imageStore);
                        }
                    }
                    
                world.setBackground(new Point(getMouse().x + 1, getMouse().y + 1), fire());
                world.setBackground(new Point(getMouse().x - 1, getMouse().y - 1), fire());
                world.setBackground(new Point(getMouse().x , getMouse().y),    fire());
                world.setBackground(new Point(getMouse().x + 1, getMouse().y), fire());
                world.setBackground(new Point(getMouse().x, getMouse().y - 1), fire());
                world.setBackground(new Point(getMouse().x + 1, getMouse().y - 1), fire());
                world.setBackground(new Point(getMouse().x + 1, getMouse().y - 2), fire());
                world.setBackground(new Point(getMouse().x + 2, getMouse().y - 3), fire());
                world.setBackground(new Point(getMouse().x - 1, getMouse().y + 1), fire());
                world.setBackground(new Point(getMouse().x - 2, getMouse().y + 2), fire());
                world.setBackground(new Point(getMouse().x - 1, getMouse().y + 2), fire());
                world.setBackground(new Point(getMouse().x + 2, getMouse().y), fire());
                world.setBackground(new Point(getMouse().x - 1, getMouse().y), fire());
                world.setBackground(new Point(getMouse().x - 2, getMouse().y), fire());
                world.setBackground(new Point(getMouse().x, getMouse().y + 1), fire());
            }
        }
    }

    public static boolean parseBackground(String[] properties,WorldModel world, ImageStore imageStore) 
    {
        if (properties.length == BGND_NUM_PROPERTIES) 
        {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    public static boolean parseMiner(String[] properties, WorldModel world,ImageStore imageStore) 
    {
        if (properties.length == MINER_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            Entity entity = Point.createMinerNotFull(properties[MINER_ID],
                    Integer.parseInt(properties[MINER_LIMIT]),
                    pt,
                    Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                    imageStore.getImageList(MINER_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    public static boolean parseObstacle(String[] properties, WorldModel world,ImageStore imageStore) 
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) 
        {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = Point.createObstacle(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }


    public static boolean parseOre(String[] properties, WorldModel world,ImageStore imageStore) 
    {
        if (properties.length == ORE_NUM_PROPERTIES) 
        {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            Entity entity = Point.createOre(properties[ORE_ID],
                    pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                    imageStore.getImageList(ORE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }

    public static boolean parseSmith(String[] properties, WorldModel world, ImageStore imageStore) 
    {
        if (properties.length == SMITH_NUM_PROPERTIES) 
        {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = Point.createBlacksmith(properties[SMITH_ID],
                    pt, imageStore.getImageList(SMITH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    public static boolean parseVein(String[] properties, WorldModel world, ImageStore imageStore) 
    {
        if (properties.length == VEIN_NUM_PROPERTIES) 
        {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            Entity entity = Point.createVein(properties[VEIN_ID],
                    pt,
                    Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                    imageStore.getImageList(VEIN_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }

}