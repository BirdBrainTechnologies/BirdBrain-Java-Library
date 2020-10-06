import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Reacts to the finch compass. Is straight when the finch's beak is pointing north.
 */
public class Compass extends FinchActor
{
    /**
     * Act - do whatever the Compass wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setRotation(getFinch().getCompass());
    }  
}
