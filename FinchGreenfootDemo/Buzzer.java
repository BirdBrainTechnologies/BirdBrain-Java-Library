import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A buzzer, causes the finch to buzz when clicked.
 * 
 * @author Michael Berry
 * @version 01/12/10
 */
public class Buzzer  extends Actor
{
    /**
     * Act - do whatever the Buzzer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi != null && mi.getClickCount()==1 && mi.getActor()==this) {
            GreenFinch.get().buzz(60, 1);
        }
    }    
}
