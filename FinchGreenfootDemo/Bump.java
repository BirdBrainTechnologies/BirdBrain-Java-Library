import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A block of colour in the world that changes to a different colour upon a "bump".
 * 
 * This class is based on the original 2010 Bump by Michael Berry
 */
public class Bump  extends FinchActor
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
    
    public void act() {
        boolean isHit = false;
        if(getFinch().getDistance() < 20)
            isHit = true;
        setBumped(isHit);
    }
}
