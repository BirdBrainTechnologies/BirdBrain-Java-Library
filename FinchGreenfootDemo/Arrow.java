import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

/**
 * An arrow, subclasses of this highlight when their corresponding arrow is pressed. This class
 * will also drive the finch depending on key input.
 * 
 * This class is based on the original 2010 Arrow by Michael Berry
 */
public abstract class Arrow extends FinchActor
{

    public static final GreenfootImage normalImage = new GreenfootImage("arrow.jpg");
    public static final GreenfootImage tintImage = new GreenfootImage("arrow_tint.jpg");

    public void tintImage() {
        setImage(tintImage);
    }
    
    public void normalImage() {
        setImage(normalImage);
    }
    
    
    public void act() {
        if(Greenfoot.isKeyDown("up")) {
            getFinch().setMotors(100, 100);
        }
        else if(Greenfoot.isKeyDown("down")) {
            getFinch().setMotors(-100, -100);
        }
        else if(Greenfoot.isKeyDown("left")) {
            getFinch().setMotors(-100, 100);
        }
        else if(Greenfoot.isKeyDown("right")) {
            getFinch().setMotors(100, -100);
        }
        else {
            getFinch().setMotors(0,0);
        }
    }
}
