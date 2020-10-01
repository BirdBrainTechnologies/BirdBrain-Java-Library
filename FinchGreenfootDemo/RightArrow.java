import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RightArrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RightArrow  extends Arrow
{
    /**
     * Act - do whatever the RightArrow wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.isKeyDown("right")) {
            tintImage();
        }
        else {
            normalImage();
        }
    }    
}
