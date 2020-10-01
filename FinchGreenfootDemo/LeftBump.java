import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Activates when the left infra-red sensor detects an obstacle.
 * 
 * @author Michael Berry
 * @version 01/12/10
 */
public class LeftBump  extends Bump
{
    /**
     * Act - do whatever the LeftBump wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.setBumped(GreenFinch.get().isLeftHit());
    }    
}
