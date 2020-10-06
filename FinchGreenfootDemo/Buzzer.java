import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A buzzer, causes the finch to buzz when clicked.
 * 
 * This class is based on the original 2010 Buzzer by Michael Berry
 */
public class Buzzer  extends FinchActor
{
    /**
     * Act - do whatever the Buzzer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi != null && mi.getClickCount()==1 && mi.getActor()==this) {
            getFinch().playNote(60, 1);
        }
    }    
}
