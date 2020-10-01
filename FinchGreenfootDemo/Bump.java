import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A block of colour in the world that changes to a different colour upon a "bump".
 * 
 * @author Michael Berry
 * @version 01/12/10
 */
public class Bump  extends Actor
{

    public Bump() {
        GreenfootImage image = new GreenfootImage(50,50);
        setImage(image);
    }


    public void setBumped(boolean bumped) {
        if(bumped) {
            getImage().setColor(greenfoot.Color.RED);
        }
        else {
            getImage().setColor(greenfoot.Color.GREEN);
        }
        getImage().fillRect(0,0,getImage().getWidth(),getImage().getHeight());
    }
}
