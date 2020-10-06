import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Set the block based on the finch's right light sensor.
 */
public class RightLight  extends LightBlock
{
    /**
     * Act - do whatever the RightLight wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.setBlock(getFinch().getLight("R"));
    }    
}
