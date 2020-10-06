import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Set the block based on the average of the finch's light sensors.
 */
public class AverageLight  extends LightBlock
{
    /**
     * Act - do whatever the AverageLight wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        double left = getFinch().getLight("L");
        double right = getFinch().getLight("R");
        super.setBlock((left + right)/2);
    }    
}
