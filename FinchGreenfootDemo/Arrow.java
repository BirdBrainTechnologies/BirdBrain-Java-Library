import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

/**
 * An arrow, subclasses of this highlight when their corresponding arrow is pressed. This class
 * will also drive the finch depending on key input.
 * 
 * @author Michael Berry 
 * @version 01/12/10
 */
public abstract class Arrow  extends Actor
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
            GreenFinch.get().setMotors(100, 100);
        }
        else if(Greenfoot.isKeyDown("down")) {
            GreenFinch.get().setMotors(-100, -100);
        }
        else if(Greenfoot.isKeyDown("left")) {
            GreenFinch.get().setMotors(-100, 100);
        }
        else if(Greenfoot.isKeyDown("right")) {
            GreenFinch.get().setMotors(100, -100);
        }
        else {
            GreenFinch.get().setMotors(0,0);
        }
    }
}
