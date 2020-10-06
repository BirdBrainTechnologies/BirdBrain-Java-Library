import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.PrintStream;
import java.io.OutputStream;

/**
 * The world where all the finch's demo components are stored.
 * 
 * This class is based on the original 2010 FinchWorld by Michael Berry
 */
public class FinchWorld  extends World
{

    private Slider redSlider, greenSlider, blueSlider;
    private Slider xSlider, ySlider, zSlider;
    private Finch finch;

    public FinchWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        prepare();
    }
    
    /**
     * Manage the sliders.
     */
    public void act() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        getFinch().setBeak(red, green, blue);
        getFinch().setTail("all", red, green, blue);
        double [] acceleration = getFinch().getAcceleration();
        int xAccel = (int)(acceleration[0]*5+50);
        xSlider.setValue(xAccel);
        int yAccel = (int)(acceleration[1]*5+50);
        ySlider.setValue(yAccel);
        int zAccel = (int)(acceleration[2]*5+50);
        zSlider.setValue(zAccel);
    }

    /**
     * Start the finch when the scenario starts. Don't remove this!!
     */
    @Override
    public void started() {
        startFinch();
    }

    /**
     * Stops the finch when the scenario stops. Don't remove this!!
     */
    @Override
    public void stopped() {
        stopFinch();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        redSlider = new Slider();
        greenSlider = new Slider();
        blueSlider = new Slider();
        addObject(new Label("Red: "),145,63);
        addObject(new Label("Green: "),145,93);
        addObject(new Label("Blue: "),145,123);
        addObject(new Label("Light Control:"),275,33);
        addObject(redSlider, 275, 63);
        addObject(greenSlider, 275, 93);
        addObject(blueSlider, 275, 123);
        addObject(new UpArrow(),250,180);
        addObject(new DownArrow(),250,320);
        addObject(new LeftArrow(),170,250);
        addObject(new RightArrow(),330,250);
        xSlider = new Slider();
        ySlider = new Slider();
        zSlider = new Slider();
        xSlider.showValue(false);
        ySlider.showValue(false);
        zSlider.showValue(false);
        addObject(new Label("Acceleration:"),495,33);
        addObject(new Label("X: "),375,63);
        addObject(new Label("Y: "),375,93);
        addObject(new Label("Z: "),375,123);
        addObject(xSlider, 495, 63);
        addObject(ySlider, 495, 93);
        addObject(zSlider, 495, 123);
        addObject(new Bump(),445,195);
        addObject(new LeftLight(),410,285);
        addObject(new AverageLight(),480,285);
        addObject(new RightLight(),550,285);

        addObject(new Label("Buzz!! "),445,368);
        addObject(new Buzzer(),480,360);

        addObject(new Label("Use the   "), 255,250);
        addObject(new Label("arrow keys!"), 250,265);
        
        addObject(new Compass(),549,200);
        addObject(new Label("N  "), 545,170);
        addObject(new Label("E  "), 590,210);
        addObject(new Label("W  "), 500,210);
        addObject(new Label("S  "), 545,250);
    }
    
    /**
     * Start the finch.
     */
    private void startFinch() {
        System.out.print("Connecting...");
        stopFinch();
        PrintStream ps = System.out;
        System.setOut(new PrintStream(new OutputStream(){public void write(int b){}}));
        finch = new Finch();
        System.setOut(ps);
        System.out.println("Done");
    }
    
    /**
     * Stop the finch.
     */
    private void stopFinch() {
        if(finch != null) {
            System.out.print("Stopping...");
            finch.stopAll();
            finch.disconnect();
            System.out.println("Done");
            finch = null;
        }
    }
    
    /**
     * Get the current finch object.
     * @return the raw finch object.
     */
    public Finch getFinch() {
        if(finch==null) {
            startFinch();
        }
        return finch;
    }
}
