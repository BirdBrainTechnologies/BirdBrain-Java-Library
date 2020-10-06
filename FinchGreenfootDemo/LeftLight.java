import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Set the block based on the finch's left light sensor.
 */
public class LeftLight  extends LightBlock
{
    /**
     * Act - do whatever the LeftLight wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.setBlock(getFinch().getLight("L"));
    }
}
