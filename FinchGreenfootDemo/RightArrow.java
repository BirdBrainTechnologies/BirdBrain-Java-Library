import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The right arrow image.
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
