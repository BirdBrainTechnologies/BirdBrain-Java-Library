import com.birdbraintechnologies.Finch;
import java.awt.Color;
import java.io.PrintStream;
import java.io.OutputStream;

/**
 * <p>A simplified version of the finch object. If you want the full
 * capabilities of the finch object then you can call the 
 * getFinchObject() method which returns the original finch 
 * backing this GreenFinch.</p>
 * <p>This class is a singleton class, you don't create new instances
 * of it since there's only one Greenfinch. Instead, call the 
 * get() method like so:</p>
 * <pre>GreenFinch finch = GreenFinch.get();</pre>
 * <p>You can then call any of the desired methods on this 
 * finch.</p>
 * @author Michael Berry (mjrb4)
 * @version 01/12/11
 */
public class GreenFinch
{
    private static final GreenFinch greenFinch = new GreenFinch();
    private Finch finch;

    private GreenFinch(){}
    
    /**
     * Get the instance of the GreenFinch. Call this instead
     * of using the constructor.
     * @return the GreenFinch singleton instance.
     */
    public static GreenFinch get() {
        return greenFinch;
    }
    
    /**
     * Get the value of the X acceleration the finch is
     * currently experiencing.
     * @return the accelerometer's X value.
     */
    public double getXAcceleration() {
        return finch.getAcceleration()[0];
    }
    
    /**
     * Get the value of the Y acceleration the finch is
     * currently experiencing.
     * @return the accelerometer's Y value.
     */
    public double getYAcceleration() {
        return finch.getAcceleration()[1];
    }
    
    /**
     * Get the value of the Z acceleration the finch is
     * currently experiencing.
     * @return the accelerometer's Z value.
     */
    public double getZAcceleration() {
        return finch.getAcceleration()[2];
    }
    
    /**
     * Determine if the left-hand infrared sensor
     * is registering something directly in front of it.
     * @return true if the left-hand sensor is registering
     * a collision, false otherwise.
     */
    public boolean isLeftHit() {
        if(finch.getDistance() < 20)
            return true;
        else
            return false;
    }
    
    /**
     * Determine if the right-hand infrared sensor
     * is registering something directly in front of it.
     * @return true if the right-hand sensor is registering
     * a collision, false otherwise.
     */
    public boolean isRightHit() {
        if(finch.getDistance() < 20)
            return true;
        else
            return false;
    }
    



    /**
     * Set the LED on board the finch to a certain color.
     * @param red Intensity of the red LED (range is 0-255)
     * @param green Intensity of the green LED (range is 0-255)
     * @param blue Intensity of the blue LED (range is 0-255)
     */
    public void setLED(int red, int green, int blue) {
        finch.setBeak(red, green, blue);
        finch.setTail("all", red, green, blue);
        
    }
    
   
    /**
     * Get the overall light level as measured by the finch.
     * @return the current light level, between 0 and 100
     * (0 being very dark and 100 being very light.)
     */
    public int getLightLevel() {
        int[] vals = new int[2];
        vals[0] = finch.getLight("L");
        vals[1] = finch.getLight("R");
                
        int sum = 0;
        for(int val : vals) {
            sum += val;
        }
        return (int)(((sum/vals.length)/255.0)*100);
    }
    
    /**
     * Get the left light level as measured by the finch.
     * @return the current light level, between 0 and 100
     * (0 being very dark and 100 being very light.)
     */
    public int getLeftLightLevel() {
        int val = finch.getLight("L");
        
        return (int)((val/255.0)*100);
    }
 
    /**
     * Get the right light level as measured by the finch.
     * @return the current light level, between 0 and 100
     * (0 being very dark and 100 being very light.)
     */
    public int getRightLightLevel() {
        int val = finch.getLight("R");
        
        return (int)((val/255.0)*100);
    }
    
    /**
     * Tell the finch to buzz at the given frequency for a 
     * certain amount of time. The range of human hearing is
     * around 20Hz to 20,000Hz, so pick a value in this range.
     * @param frequency Hz that the finch should buzz at.
     * @param duration length of time the buzz should 
     * occur for, in ms (1000ms = 1s.)
     */
    public void buzz(int note, int beats) {
        finch.playNote(note, beats);
    }
    
    /**
     * Set the left and right wheel speeds of the finch. The 
     * values are taken between -100 and 100; -100 is full 
     * speed backwards, 0 is stopped and 100 is full speed 
     * forwards.
     * @param left the speed of the left wheel.
     * @param right the speed of the right wheel.
     */
    public void setMotors(int left, int right) {
    
        finch.setMotors(left, right);
    }
    
    /**
     * Start the finch.
     */
    protected void start() {
        System.out.print("Connecting...");
        stop();
        PrintStream ps = System.out;
        System.setOut(new PrintStream(new OutputStream(){public void write(int b){}}));
        finch = new Finch();
        System.setOut(ps);
        System.out.println("Done");
    }
    
    /**
     * Stop the finch.
     */
    protected void stop() {
        
        if(finch != null) {
            System.out.print("Stopping...");
            finch.stopAll();
            finch.disconnect();
            System.out.println("Done");
            finch = null;
        }
    }
    
    /**
     * Get the finch object underlying this GreenFinch.
     * @return the raw finch object.
     */
    public Finch getFinchObject() {
        if(finch==null) {
            start();
        }
        return finch;
    }
}
