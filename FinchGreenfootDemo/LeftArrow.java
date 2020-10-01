import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LeftArrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LeftArrow  extends Arrow
{

    public void addedToWorld(World world) {
        setRotation(180);
    }
    
    /**
     * Act - do whatever the LeftArrow wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.isKeyDown("left")) {
            tintImage();
        }
        else {
            normalImage();
        }
    }    
}
