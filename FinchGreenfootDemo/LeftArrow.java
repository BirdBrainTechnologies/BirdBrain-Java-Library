import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The left arrow image.
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
