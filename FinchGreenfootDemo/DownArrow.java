import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DownArrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DownArrow  extends Arrow
{
    public void addedToWorld(World world) {
        setRotation(90);
    }
    
    /**
     * Act - do whatever the DownArrow wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.isKeyDown("down")) {
            tintImage();
        }
        else {
            normalImage();
        }
    }    
}
