import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The up arrow image.
 */
public class UpArrow  extends Arrow
{

    public void addedToWorld(World world) {
        setRotation(-90);
    }
    
    /**
     * Act - do whatever the UpArrow wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        if(Greenfoot.isKeyDown("up")) {
            tintImage();
        }
        else {
            normalImage();
        }
    }    
}
